package sabbat.location.infrastructure.service.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * Created by bruenni on 07.09.16.
 */
@Component
public class RabbitMqActivityRabbitListener {

    Logger logger = LoggerFactory.getLogger(RabbitMqActivityRabbitListener.class);

    @RabbitListener(queues = "${location.activity.queue.command}",
                    containerFactory = "rabbitListenerContainerFactory")
    //@Header("correlationId") String correlationId
    public Message onMessage(Message message, @Header(AmqpHeaders.CORRELATION_ID) byte[] correlationId) {
        logger.debug("-----> " + message.toString() + "cid=" + correlationId);

        MessageProperties msgProps = MessagePropertiesBuilder.newInstance().setCorrelationId(correlationId).build();

        Message response = MessageBuilder.withBody(StandardCharsets.UTF_8.encode("{result: \"OK\"}").array()).andProperties(msgProps).build();

        logger.debug("<----- " + response.toString());

        //return new AsyncResult<Message>(response);
        return response;
    }
}
