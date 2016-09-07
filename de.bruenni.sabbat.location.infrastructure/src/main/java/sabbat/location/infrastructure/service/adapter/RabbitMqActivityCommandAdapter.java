package sabbat.location.infrastructure.service.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * Created by bruenni on 07.09.16.
 */
public class RabbitMqActivityCommandAdapter implements MessageListener {

    Logger logger = LoggerFactory.getLogger(RabbitMqActivityCommandAdapter.class);

    @Override
    public void onMessage(Message message) {
        logger.debug(message.toString());
    }
}
