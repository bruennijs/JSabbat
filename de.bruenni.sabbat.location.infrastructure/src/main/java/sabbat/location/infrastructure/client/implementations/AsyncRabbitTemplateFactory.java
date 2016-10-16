package sabbat.location.infrastructure.client.implementations;

import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.core.task.TaskExecutor;
import org.springframework.util.Assert;

import java.util.concurrent.Executor;

/**
 * Created by bruenni on 15.10.16.
 */
public class AsyncRabbitTemplateFactory {
    /**
     * Creates with own taskexecutor.
     * @param connectionFactory
     * @param exchange
     * @param routingKey
     * @param replyQueue
     * @param taskExecutor
     * @return
     */
    public static AsyncRabbitTemplate create(ConnectionFactory connectionFactory, String exchange, String routingKey, String replyQueue, Executor taskExecutor) {
        Assert.notNull(connectionFactory, "'connectionFactory' cannot be null");
        Assert.notNull(routingKey, "'routingKey' cannot be null");
        Assert.notNull(replyQueue, "'replyQueue' cannot be null");
        RabbitTemplate templateTmp = new RabbitTemplate(connectionFactory);
        templateTmp.setExchange(exchange == null ? "" : exchange);
        templateTmp.setRoutingKey(routingKey);
        SimpleMessageListenerContainer containerTmp = new SimpleMessageListenerContainer(connectionFactory);
        containerTmp.setQueueNames(replyQueue);
        containerTmp.afterPropertiesSet();
        containerTmp.setTaskExecutor(taskExecutor);
        return new AsyncRabbitTemplate(templateTmp, containerTmp);
    }
}
