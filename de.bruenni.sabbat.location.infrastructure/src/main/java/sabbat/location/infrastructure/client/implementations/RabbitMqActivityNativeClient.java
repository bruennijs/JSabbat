package sabbat.location.infrastructure.client.implementations;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by bruenni on 04.07.16.
 */
public class RabbitMqActivityNativeClient implements IActivityRemoteService {

    final Logger logger = org.slf4j.LoggerFactory.getLogger(RabbitMqActivityNativeClient.class);

    @Value("${location.activity.routingkey.command.start}")
    public String StartRoutingKey;

    @Value("${location.activity.routingkey.command.stop}")
    public String StopRoutingKey;

    private AsyncRabbitTemplate asyncRabbitTemplate;
    private infrastructure.parser.IDtoParser parser;
    private ConnectionFactory connectionFactory;

    public RabbitMqActivityNativeClient(AsyncRabbitTemplate asyncRabbitTemplate,
                                        infrastructure.parser.IDtoParser parser)
    {
        this.asyncRabbitTemplate = asyncRabbitTemplate;
        this.parser = parser;
    }

    @Override
    public ListenableFuture<ActivityCreatedResponseDto> start(ActivityCreateRequestDto command) throws IOException, InterruptedException, ExecutionException, TimeoutException {

        try {
            String dtoJson = parser.serialize(command);

            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            messageProperties.setCorrelationIdString(UUID.randomUUID().toString());

            AsyncRabbitTemplate.RabbitMessageFuture responseFuture = asyncRabbitTemplate.sendAndReceive(this.StartRoutingKey, new org.springframework.amqp.core.Message(dtoJson.getBytes(StandardCharsets.US_ASCII), messageProperties));

            org.springframework.amqp.core.Message message = responseFuture.get();
            logger.debug(new String(message.getBody()));

            return new AsyncResult<>(parser.parse(message.getBody(), ActivityCreatedResponseDto.class));

/*            return new ListenableFutureAdapter<ActivityCreatedResponseDto, org.springframework.amqp.core.Message>(responseFuture) {
                @Override
                protected ActivityCreatedResponseDto adapt(org.springframework.amqp.core.Message msg) throws ExecutionException {
                    try {
                        return parser.parse(msg.getBody(), ActivityCreatedResponseDto.class);
                    } catch (IOException e) {
                        throw new ExecutionException(e);
                    }
                }
            };*/
        }
        catch (Exception exception)
        {
            logger.error("Start activity failed", exception);
            throw exception;
        }
    }

    @Override
    public CompletableFuture<ActivityStoppedResponseDto> stop(ActivityStopRequestDto command) {
        CompletableFuture future = new CompletableFuture();
        future.complete(new ActivityStoppedResponseDto(command.getId()));
        return future;
    }

    @Override
    public CompletableFuture<Void> update(ActivityUpdateRequestDto command) {
        CompletableFuture future = new CompletableFuture();
        future.complete(null);
        return future;
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
