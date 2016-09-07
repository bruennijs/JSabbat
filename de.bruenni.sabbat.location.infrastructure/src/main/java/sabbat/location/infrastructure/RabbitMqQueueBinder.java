package sabbat.location.infrastructure;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

/**
 * Created by bruenni on 07.09.16.
 */
public class RabbitMqQueueBinder {
    private RabbitAdmin template;
    private Queue queue;
    private Exchange exchange;
    private String routingKey;

    public RabbitMqQueueBinder(RabbitAdmin template, Queue queue, Exchange exchange, String routingKey) {
        this.template = template;
        this.queue = queue;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    /***
     * Binds queue to an exchange
     */
    public void bindQueuesToExchanges()
    {
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();

        template.declareBinding(binding);
    }
}
