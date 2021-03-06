package sabbat.location.api.implementations;

import infrastructure.parser.ParserException;
import infrastructure.parser.SerializingException;
import org.slf4j.Logger;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import rx.Observable;
import rx.subjects.ReplaySubject;
import sabbat.location.api.IActivityRemoteService;
import sabbat.location.api.dto.*;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by bruenni on 04.07.16.
 */
public class RabbitMqActivityRemoteService implements IActivityRemoteService {

    final Logger logger = org.slf4j.LoggerFactory.getLogger(RabbitMqActivityRemoteService.class);

    @Value("${location.activity.routingkey.command.start}")
    public String startRoutingKey;

    @Value("${location.activity.routingkey.command.stop}")
    public String stopRoutingKey;

    private AsyncRabbitTemplate asyncRabbitTemplate;
    private ReliableRabbitTemplate updateTemplate;
    private infrastructure.parser.IDtoParser parser;
    private ConnectionFactory connectionFactory;

    /**
     * Constructor
     * @param asyncRabbitTemplate
     * @param parser
     */
    public RabbitMqActivityRemoteService(AsyncRabbitTemplate asyncRabbitTemplate,
                                         ReliableRabbitTemplate updateTabbitTemplate,
                                         infrastructure.parser.IDtoParser parser)
    {
        this.asyncRabbitTemplate = asyncRabbitTemplate;
        this.updateTemplate = updateTabbitTemplate;
        this.parser = parser;

        //// enable message returns: to ensure reliable queue delivery
        asyncRabbitTemplate.setMandatory(true);
    }

    @Override
    public Observable<ActivityCreatedResponseDto> start(ActivityCreateRequestDto command, String accessToken) throws InterruptedException, ExecutionException, TimeoutException, SerializingException {

        try {
            logger.debug("Start activity [" + command.toString() + "]");

            String dtoJson = parser.serialize(command);

            MessageProperties messageProperties = buildMessageProperties(accessToken);

            AsyncRabbitTemplate.RabbitMessageFuture responseFuture = asyncRabbitTemplate.sendAndReceive(this.startRoutingKey, new org.springframework.amqp.core.Message(dtoJson.getBytes(StandardCharsets.US_ASCII), messageProperties));

            return Observable.from(responseFuture).map(msg -> {
                try {
                    return parser.parse(msg.getBody(), ActivityCreatedResponseDto.class);
                } catch (ParserException e) {
                    logger.error("Parse DTO failed", e);
                    return null;
                }
            });
        }
        catch (Exception exception)
        {
            logger.error("Start activity failed", exception);
            throw exception;
        }
    }

    @Override
    public Observable<ActivityStoppedResponseDto> stop(ActivityStopRequestDto command, String accessToken) throws SerializingException {
        try {
            logger.debug("Stop activity [" + command.toString() + "]");

            String dtoJson = parser.serialize(command);

            MessageProperties messageProperties = buildMessageProperties(accessToken);

            AsyncRabbitTemplate.RabbitMessageFuture responseFuture = asyncRabbitTemplate.sendAndReceive(this.stopRoutingKey, new org.springframework.amqp.core.Message(dtoJson.getBytes(StandardCharsets.US_ASCII), messageProperties));

            return Observable.from(responseFuture).map(msg -> {
                try {
                    return parser.parse(msg.getBody(), ActivityStoppedResponseDto.class);
                } catch (ParserException e) {
                    logger.error("Parse DTO failed", e);
                    return null;
                }
            });
        }
        catch (Exception exception)
        {
            logger.error("Start activity failed", exception);
            throw exception;
        }
    }

    private MessageProperties buildMessageProperties(String accessToken) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        messageProperties.setCorrelationIdString(UUID.randomUUID().toString());
        messageProperties.setHeader("AuthorizationToken", accessToken);
        return messageProperties;
    }

    @Override
    public Observable<Void> update(ActivityUpdateEventDto dto) throws Exception {


        try {

            String json = parser.serialize(dto);

            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            messageProperties.setCorrelationIdString(UUID.randomUUID().toString());

            logger.debug(String.format("Update activity [dto=%1s, correlationId=%2s]", json, messageProperties.getCorrelationIdString()));

            return updateTemplate.sendReliable(
              new org.springframework.amqp.core.Message(json.getBytes(StandardCharsets.US_ASCII),
                messageProperties));

        }
        catch (Exception exception)
        {
            logger.error("update activity failed", exception);
            throw exception;
        }
    }

    @Override
    public ListenableFuture<String> echoAsync(@Payload String payload, @Header("authorization") String jwt) {
        AsyncResult<String> future = new AsyncResult<>("echo reply");
        return future;
    }

    /***
     *
     * @param message
     * @return
     */
    public String echo(Message<String> message)
    {
        return "RABBIT echo[" + message.getPayload() + "]";
    }
}
