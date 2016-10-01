package sabbat.location.infrastructure.service.adapter;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureAdapter;
import rx.Observable;
import rx.Observer;
import sabbat.location.core.application.service.IActivityApplicationService;
import sabbat.location.core.application.service.command.ActivityCreateCommand;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.infrastructure.client.dto.ActivityCreateRequestDto;
import sabbat.location.infrastructure.client.dto.ActivityCreatedResponseDto;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by bruenni on 07.09.16.
 */
@Component
public class ActivityRabbitListener {

    private Logger logger = LoggerFactory.getLogger(ActivityRabbitListener.class);

    @Value("${location.activity.routingkey.command.start}")
    public String ActivityStartRoutingKey;

    @Value("${location.activity.routingkey.command.stop}")
    public String ActivityStopRoutingKey;

    @Autowired
    public IActivityApplicationService applicationService;

    @Autowired
    private infrastructure.parser.IDtoParser dtoParser;

    @RabbitListener(id="activityListener",
                    queues = "${location.activity.queue.command}",
                    containerFactory = "rabbitListenerContainerFactory")
    //@Header("correlationId") String correlationId
    public Message onMessage(Message message,
                             @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey,
                             Channel channel,
                             @Header(AmqpHeaders.CORRELATION_ID) byte[] correlationId) throws Exception {
        logger.debug("-----> " + message.toString() + "cid=" + correlationId + ",routingkey=" + routingKey);

        if (routingKey.equals(ActivityStartRoutingKey)) {

            ActivityCreateRequestDto dtoRequest = dtoParser.parse(message.getBody(), ActivityCreateRequestDto.class);

            ActivityCreateCommand command = new ActivityCreateCommand(UUID.fromString(dtoRequest.getId()), dtoRequest.getTitle());

            ListenableFuture<Activity> activityStartFuture = this.applicationService.start(command);

            //Observable.from(activityStartFuture)
            ActivityCreatedResponseDto dtoResponse = new ActivityCreatedResponseDto(activityStartFuture.get().getKey().getId().toString());

            MessageProperties msgProps = MessagePropertiesBuilder.newInstance().setCorrelationId(correlationId).build();
            return MessageBuilder.withBody(dtoParser.serialize(dtoResponse).getBytes())
                    .andProperties(msgProps)
                    .build();
            }

            //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        throw new Exception("not implemented");
    }
}
