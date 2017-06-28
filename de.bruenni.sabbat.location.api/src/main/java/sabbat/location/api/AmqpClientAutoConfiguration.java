package sabbat.location.api;

import infrastructure.parser.JsonDtoParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import sabbat.location.api.implementations.AsyncRabbitTemplateFactory;
import sabbat.location.api.implementations.RabbitMqActivityRemoteService;
import sabbat.location.api.implementations.ReliableRabbitTemplate;
import spring.rabbit.ExtendedRabbitListenerContainerFactory;

import java.util.HashMap;
import java.util.concurrent.Executor;

/**
 * Created by bruenni on 24.09.16.
 */
@Configuration
@ImportResource(locations =
        {
                "classpath:spring/spring-location-amqp-client.xml"
        })
@PropertySource("classpath:sabbat-location-api.properties")
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

        @Autowired
        @Qualifier("serviceTaskExecuter")
        public Executor taskExecutor;

        @Bean(name = "messageListenerContainerFactory")
        public ExtendedRabbitListenerContainerFactory rabbitListenerContainerFactory() {
            ExtendedRabbitListenerContainerFactory factory = new ExtendedRabbitListenerContainerFactory();
            //factory.setConcurrentConsumers(5);
            //factory.setMaxConcurrentConsumers(10);
            factory.setTaskExecutor(taskExecutor);
            factory.setConnectionFactory(connectionFactory);
            factory.setMessageConverter(jackson2JsonMessageConverter());
            factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
            factory.setAutoStartup(true);
            factory.setAutoDeclare(true);
            return factory;
        }

        @Bean(name = "locationCommandRabbitTemplate")
        @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
        public AsyncRabbitTemplate locationCommandRabbitTemplate() {
                AsyncRabbitTemplate template = AsyncRabbitTemplateFactory.create(
                        connectionFactory,
                        activityCommandExchangeName,
                        "",
                        commandReplyQueueName,
                        taskExecutor);
                template.setAutoStartup(true);
                template.start();
                return template;
        }

        @Bean(name = "locationUpdateRabbitTemplate")
        @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
        public ReliableRabbitTemplate locationUpdateRabbitTemplate() {
                ReliableRabbitTemplate template = new ReliableRabbitTemplate(connectionFactory);
                template.setExchange(trackingExchangeName);
                template.setRoutingKey(trackingUpdateRoutingKey);
                return template;
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
        public Queue locationCommandReplyQueue()
        {
                /*HashMap<String, Object> arguments = new HashMap<>();
                arguments.put("x-message-ttl", 5000);*/
                //Queue queue = new Queue(activityCommandQueueName, true, false, false, arguments);
                Queue queue = new Queue(commandReplyQueueName, false, false, true);
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

        @Bean(name = "RabbitMqActivityRemoteService")
        @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
        public IActivityRemoteService rabbitMqActivityremoteService(AsyncRabbitTemplate locationCommandRabbitTemplate, ReliableRabbitTemplate locationUpdateRabbitTemplate)
        {
                return new RabbitMqActivityRemoteService(locationCommandRabbitTemplate,
                        locationUpdateRabbitTemplate,
                        new JsonDtoParser());
        }

        @Bean(name = "jackson2JsonMessageConverter")
        @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
        public MessageConverter jackson2JsonMessageConverter()
        {
                return new Jackson2JsonMessageConverter();
        }
}
