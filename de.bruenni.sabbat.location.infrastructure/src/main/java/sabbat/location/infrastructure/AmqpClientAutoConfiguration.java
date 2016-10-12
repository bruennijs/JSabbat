package sabbat.location.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;

import java.util.HashMap;

/**
 * Created by bruenni on 24.09.16.
 */
@Configuration
@ConditionalOnProperty(prefix = "location.infrastructure.amqp.client", name = "enabled", havingValue = "true", matchIfMissing = false)
@PropertySource("classpath:sabbat-location-infrastructure.properties")
@Import(LocationInfrastructureConfiguration.class)
@ImportResource(locations =
        {
                "classpath:spring/spring-location-amqp-client.xml"
        })
public class AmqpClientAutoConfiguration {

        private Logger log = LoggerFactory.getLogger(AmqpClientAutoConfiguration.class);

        @Value("${location.activity.queue.command}")
        public String activityCommandQueueName;

        @Value("${location.activity.exchange.command}")
        public String activityCommandExchangeName;

        @Value("${location.activity.routingkey.command.all}")
        public String activityCommandsRoutingKey;

        @Value("${location.activity.routingkey.tracking.update}")
        public String trackingUpdateRoutingKey;

        @Value("${location.activity.exchange.tracking}")
        public String trackingExchangeName;

        @Value("${location.activity.queue.tracking}")
        public String trackingQueueName;

        @Value("${location.activity.queue.command.reply}")
        public String commandReplyQueueName;

        @Autowired
        @Qualifier("clientConnectionFactory")
        public ConnectionFactory connectionFactory;

        @Autowired
        @Qualifier("admin")
        public RabbitAdmin admin;

        @Bean(name = "locationCommandRabbitTemplate")
        @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
        public AsyncRabbitTemplate commandRabbitTemplate() {
                return new AsyncRabbitTemplate(connectionFactory,
                        activityCommandExchangeName,
                        activityCommandsRoutingKey,
                        commandReplyQueueName);
        }

        @Bean(name = "locationUpdateRabbitTemplate")
        @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
        public RabbitTemplate locationUpdateRabbitTemplate() {
                RabbitTemplate template = new RabbitTemplate(connectionFactory);
                template.setExchange(trackingExchangeName);
                template.setRoutingKey(trackingUpdateRoutingKey);
                return template;

/*                return new AsyncRabbitTemplate(connectionFactory,
                        trackingExchangeName,
                        trackingUpdateRoutingKey);*/
        }

        @Bean
        public Queue locationCommandQueue()
        {
                log.debug("locationCommandQueue definition [admin=" + admin.toString() + "]");

/*                HashMap<String, Object> arguments = new HashMap<>();
                arguments.put("x-message-ttl", 5000);*/
                //Queue queue = new Queue(activityCommandQueueName, true, false, false, arguments);
                Queue queue = new Queue(activityCommandQueueName, true, false, false);
                queue.setAdminsThatShouldDeclare(admin);
                queue.setShouldDeclare(true);
                return queue;
        }

        @Bean
        public org.springframework.amqp.core.Exchange locationCommandExchange()
        {
                return ExchangeBuilder.topicExchange(activityCommandExchangeName).autoDelete().build();
        }

        @Bean
        public Binding locationCommandBinding()
        {
                return BindingBuilder
                        .bind(locationCommandQueue())
                        .to(locationCommandExchange())
                        .with(activityCommandsRoutingKey)
                        .noargs();
        }

        @Bean
        public org.springframework.amqp.core.Exchange locationTrackingExchange()
        {
                return ExchangeBuilder.topicExchange(trackingExchangeName).autoDelete().build();
        }

        @Bean
        public Queue locationTrackingQueue()
        {
                HashMap<String, Object> arguments = new HashMap<>();
                arguments.put("x-max-length-bytes", (int) 50 * 1024 * 1024);
                Queue queue = new Queue(trackingQueueName, true, false, false, arguments);
                queue.setAdminsThatShouldDeclare(admin);
                queue.setShouldDeclare(true);
                return queue;
        }

        @Bean
        public Binding locationTrackingBinding()
        {
                return BindingBuilder
                        .bind(locationTrackingQueue())
                        .to(locationTrackingExchange())
                        .with(trackingUpdateRoutingKey)
                        .noargs();
        }

}
