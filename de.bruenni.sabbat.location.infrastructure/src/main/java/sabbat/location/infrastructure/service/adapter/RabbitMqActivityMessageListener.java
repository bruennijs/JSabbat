package sabbat.location.infrastructure.service.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;

import java.nio.charset.StandardCharsets;

/**
 * Created by bruenni on 07.09.16.
 */
public class RabbitMqActivityMessageListener implements MessageListener {

    Logger logger = LoggerFactory.getLogger(RabbitMqActivityMessageListener.class);

    public RabbitMqActivityMessageListener() {
    }

    @Override
    public void onMessage(Message message)
    {
        logger.debug("-----> ", message.toString());
    }
}
