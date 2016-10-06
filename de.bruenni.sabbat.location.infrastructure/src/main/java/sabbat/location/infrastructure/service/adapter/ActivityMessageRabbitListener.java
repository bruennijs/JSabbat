package sabbat.location.infrastructure.service.adapter;

import com.rabbitmq.client.Channel;
import infrastructure.parser.IDtoParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import sabbat.location.core.application.service.IActivityApplicationService;
import sabbat.location.core.application.service.command.ActivityCreateCommand;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.infrastructure.client.dto.ActivityCreateRequestDto;
import sabbat.location.infrastructure.client.dto.ActivityCreatedResponseDto;
import org.springframework.messaging.Message;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Created by bruenni on 07.09.16.
 */
@Component
public class ActivityMessageRabbitListener {

    private Logger logger = LoggerFactory.getLogger(ActivityMessageRabbitListener.class);

    @Value("${location.activity.routingkey.command.start}")
    public String ActivityStartRoutingKey;

    @Autowired
    public IActivityApplicationService applicationService;

    @RabbitListener(queues = "${location.activity.queue.command}",
                    id = "activityMessageRabbitListener",
                    containerFactory = "rabbitListenerContainerFactory")
    //@Header("correlationId") String correlationId
    public Message<ActivityCreatedResponseDto> onMessage(Message<ActivityCreateRequestDto> requestMsg,
                                                         @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey,
                                                         @Header(AmqpHeaders.REPLY_TO) String replyTo,
                                                                                       Channel channel,
                                                                                       @Header(AmqpHeaders.CORRELATION_ID) byte[] correlationId) throws Exception {
        logger.debug("-----> " + requestMsg.toString() + "cid=" + correlationId + ",routingkey=" + routingKey + ",replyTo=" + replyTo);

        if (routingKey.equals(ActivityStartRoutingKey)) {

            ActivityCreateCommand command = new ActivityCreateCommand(UUID.randomUUID().toString(), requestMsg.getPayload().getId(), requestMsg.getPayload().getTitle());

            Activity activity = this.applicationService.start(command);

            //Observable.from(activityStartFuture)
            ActivityCreatedResponseDto dtoResponse = new ActivityCreatedResponseDto(activity.getKey().getId().toString());

            return MessageBuilder.withPayload(dtoResponse)
                    .setHeader(AmqpHeaders.CONTENT_TYPE, "application/json")
                    .setHeader(AmqpHeaders.CORRELATION_ID, correlationId)
                    .build();
        }

        //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        throw new Exception("not supported" + requestMsg.toString());
    }
}
