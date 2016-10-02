package sabbat.location.infrastructure.client.implementations;

import org.slf4j.Logger;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureAdapter;
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

    @Value("${location.activity.routingkey.command.start}")
    public String StartRoutingKey;

    @Value("${location.activity.routingkey.command.stop}")
    public String StopRoutingKey;

    private AsyncRabbitTemplate asyncRabbitTemplate;
    private infrastructure.parser.IDtoParser parser;
    private ConnectionFactory connectionFactory;

    public RabbitMqActivityRemoteService(AsyncRabbitTemplate asyncRabbitTemplate,
                                         infrastructure.parser.IDtoParser parser)
    {
        this.asyncRabbitTemplate = asyncRabbitTemplate;
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

            AsyncRabbitTemplate.RabbitMessageFuture responseFuture = asyncRabbitTemplate.sendAndReceive(this.StartRoutingKey, new org.springframework.amqp.core.Message(dtoJson.getBytes(StandardCharsets.US_ASCII), messageProperties));

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
        return new AsyncResult<>(new ActivityStoppedResponseDto(command.getId()));
    }

    @Override
    public void update(ActivityUpdateEventDto dto) throws Exception {
        throw new Exception("not implemented");
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
