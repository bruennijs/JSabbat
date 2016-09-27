package sabbat.location.infrastructure.service.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.nio.charset.StandardCharsets;

/**
 * Created by bruenni on 07.09.16.
 */
public class ActivityEventAdapter {

    Logger logger = LoggerFactory.getLogger(ActivityEventAdapter.class);

    public ActivityEventAdapter() {
    }

    public Message onMessage(@Payload String payload) {
        logger.debug("-----> [correlationid=" + payload.toString() + "payload=" + payload);

        MessageProperties msgProps = MessagePropertiesBuilder.newInstance().build();

        Message response = MessageBuilder.withBody(StandardCharsets.UTF_8.encode("{result: \"OK\"}").array()).andProperties(msgProps).build();

        logger.debug("<----- ", response.toString());

        return response;//"response text of ActivityEventAdapter";
    }
}
