package sabbat.location.infrastructure.client.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.impl.SLF4JLogFactory;
import org.slf4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import rx.Observable;
import rx.subjects.PublishSubject;
import sabbat.location.infrastructure.client.IActivityRemoteService;
import sabbat.location.infrastructure.client.dto.*;
import sabbat.location.infrastructure.client.parser.IDtoParser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static rx.subjects.PublishSubject.create;

/**
 * Created by bruenni on 04.07.16.
 */
public class RabbitMqActivityNativeClient implements IActivityRemoteService {

    final Logger logger = org.slf4j.LoggerFactory.getLogger(RabbitMqActivityNativeClient.class);

    @Value("{location.activity.routingkey.command.start}")
    public String StartRoutingKey;

    @Value("{location.activity.routingkey.command.stop}")
    public String StopRoutingKey;

    private AsyncRabbitTemplate asyncRabbitTemplate;
    private IDtoParser parser;
    private ConnectionFactory connectionFactory;

    public RabbitMqActivityNativeClient(AsyncRabbitTemplate asyncRabbitTemplate,
                                        IDtoParser parser)
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
            messageProperties.setCorrelationId(UUID.randomUUID().toString().getBytes(StandardCharsets.US_ASCII));

            AsyncRabbitTemplate.RabbitMessageFuture responseFuture = asyncRabbitTemplate.sendAndReceive(this.StartRoutingKey, new org.springframework.amqp.core.Message(dtoJson.getBytes(StandardCharsets.US_ASCII), messageProperties));

            org.springframework.amqp.core.Message responseMessage = responseFuture.get(5000, TimeUnit.MILLISECONDS);

            ActivityCreatedResponseDto responseDto = parser.parse(new String(responseMessage.getBody(), StandardCharsets.UTF_8), ActivityCreatedResponseDto.class);

            return new AsyncResult<ActivityCreatedResponseDto>(new ActivityCreatedResponseDto("tbd"));
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