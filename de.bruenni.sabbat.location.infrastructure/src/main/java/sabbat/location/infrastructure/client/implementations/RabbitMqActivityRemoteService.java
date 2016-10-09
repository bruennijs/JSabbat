package sabbat.location.infrastructure.client.implementations;

import com.sun.javafx.binding.StringFormatter;
import org.slf4j.Logger;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureAdapter;
import rx.subjects.ReplaySubject;
import sabbat.location.infrastructure.client.Confirmation;
import sabbat.location.infrastructure.client.IActivityRemoteService;
import sabbat.location.infrastructure.client.dto.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by bruenni on 04.07.16.
 */
public class RabbitMqActivityRemoteService implements IActivityRemoteService {

    final Logger logger = org.slf4j.LoggerFactory.getLogger(RabbitMqActivityRemoteService.class);

    private AsyncRabbitTemplate asyncRabbitTemplate;
    private RabbitTemplate updateTemplate;
    private infrastructure.parser.IDtoParser parser;
    private ConnectionFactory connectionFactory;

    /**
     * Constructor
     * @param asyncRabbitTemplate
     * @param parser
     */
    public RabbitMqActivityRemoteService(AsyncRabbitTemplate asyncRabbitTemplate,
                                         RabbitTemplate updateTabbitTemplate,
                                         infrastructure.parser.IDtoParser parser)
    {
        this.asyncRabbitTemplate = asyncRabbitTemplate;
        this.updateTemplate = updateTabbitTemplate;
        this.parser = parser;
    }

    @Override
    public ListenableFuture<ActivityCreatedResponseDto> start(ActivityCreateRequestDto command) throws IOException, InterruptedException, ExecutionException, TimeoutException {

        try {
            logger.debug("Start activity [" + command.toString() + "]");

            String dtoJson = parser.serialize(command);

            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            messageProperties.setCorrelationIdString(UUID.randomUUID().toString());

            AsyncRabbitTemplate.RabbitMessageFuture responseFuture = asyncRabbitTemplate.sendAndReceive(new org.springframework.amqp.core.Message(dtoJson.getBytes(StandardCharsets.US_ASCII), messageProperties));

            return new ListenableFutureAdapter<ActivityCreatedResponseDto, org.springframework.amqp.core.Message>(responseFuture) {
                @Override
                protected ActivityCreatedResponseDto adapt(org.springframework.amqp.core.Message msg) throws ExecutionException {
                    try {
                        return parser.parse(msg.getBody(), ActivityCreatedResponseDto.class);
                    } catch (IOException e) {
                        throw new ExecutionException(e);
                    }
                }
            };
        }
        catch (Exception exception)
        {
            logger.error("Start activity failed", exception);
            throw exception;
        }
    }

    @Override
    public ListenableFuture<ActivityStoppedResponseDto> stop(ActivityStopRequestDto command) {
        //return AsyncResult.forExecutionException(new Exception("thrown in RabbitMqActRemoteService"));
        return new AsyncResult<>(new ActivityStoppedResponseDto(command.getId()));
    }

    @Override
    public rx.Observable<Confirmation<ActivityUpdateEventDto>> update(ActivityUpdateEventDto dto) throws Exception {


        try {

            ReplaySubject<Confirmation<ActivityUpdateEventDto>> subject = ReplaySubject.create();

            String json = parser.serialize(dto);

            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            messageProperties.setCorrelationIdString(UUID.randomUUID().toString());

            logger.debug(String.format("Update activity [dto=%1s, correlationId=%2s]", json, messageProperties.getCorrelationIdString()));

            updateTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
                logger.debug(String.format("update return %1s,%2s", replyCode, replyText));
                try {
                    subject.onNext(new Confirmation<>(dto, true, replyText));
                }
                finally {
                    subject.onCompleted();
                }
            });

            updateTemplate.setConfirmCallback((correlationData, ack, cause) ->
            {
                try
                {
                    logger.debug(String.format("update confirmation [correlationId=%1s, %2s, cause=%3s]", correlationData, ack, cause));
                    //subject.onNext(new Confirmation<>(dto, ack, cause));
                }
                finally {
                    //subject.onCompleted();
                }
            });

            updateTemplate.send(
              new org.springframework.amqp.core.Message(json.getBytes(StandardCharsets.US_ASCII),
                messageProperties));

            return subject;
        }
        catch (Exception exception)
        {
            logger.error("Start activity failed", exception);
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
