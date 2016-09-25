package sabbat.location.infrastructure.service.adapter;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import sabbat.location.infrastructure.client.dto.ActivityCreateRequestDto;
import sabbat.location.infrastructure.client.dto.ActivityCreatedResponseDto;
import org.springframework.messaging.Message;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by bruenni on 07.09.16.
 */
@Component
public class ActivityMessageRabbitListener {

    Logger logger = LoggerFactory.getLogger(ActivityMessageRabbitListener.class);

    @RabbitListener(queues = "${location.activity.queue.command}",
                    containerFactory = "rabbitListenerContainerFactory")
    //@Header("correlationId") String correlationId
    public Message<ActivityCreatedResponseDto> onMessage(Message<ActivityCreateRequestDto> message,
                                                                                       Channel channel,
                                                                                       @Header(AmqpHeaders.CORRELATION_ID) byte[] correlationId) throws IOException {
        logger.debug("-----> " + message.toString() + "cid=" + correlationId);


        MessageProperties msgProps = MessagePropertiesBuilder.newInstance().setCorrelationId(correlationId).build();

        GenericMessage<ActivityCreatedResponseDto> response = new GenericMessage<>(new ActivityCreatedResponseDto("4711"));

        logger.debug("<----- " + response.toString());

        //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        //return new AsyncResult<Message>(response);
        return response;
    }
}
