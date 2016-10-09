package sabbat.location.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;
import sabbat.location.infrastructure.client.configuration.LocationInfrastructureConfiguration;
import sabbat.location.infrastructure.common.ExtendedRabbitListenerContainerFactory;

import java.util.HashMap;
import java.util.concurrent.Executor;

/**
 * Created by bruenni on 24.09.16.
 */
@Configuration
@ConditionalOnProperty(prefix = "location.infrastructure.amqp.service",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = false)
@PropertySource("classpath:sabbat-location-infrastructure.properties")
@Import(LocationInfrastructureConfiguration.class)
@ImportResource(locations =
        {
                "classpath:spring/spring-location-amqp-service.xml"
        })
@EnableRabbit   //IMPORT: to use @RabbitListener annotated classes -> Moreover a SimpleMessageListenerContainerFactory can be registered to customize created container
public class AmqpServiceAutoConfiguration implements RabbitListenerConfigurer {

        private Logger log = LoggerFactory.getLogger(AmqpServiceAutoConfiguration.class);

        @Value("${location.activity.queue.command}")
        public String activityCommandQueueName;

        @Value("${location.activity.queue.tracking}")
        public String activityTrackingQueueName;

        @Value("${location.activity.exchange.command}")
        public String activityCommandExchangeName;

        @Value("${location.activity.exchange.tracking}")
        public String activityTrackingExchangeName;

        @Value("${location.activity.routingkey.command.all}")
        public String activityCommandsRoutingKey;

        @Value("${location.activity.routingkey.tracking.update}")
        public String activityTrackingUpdateRoutingKey;

        @Autowired
        @Qualifier("serviceAdmin")
        public RabbitAdmin serviceAdmin;

        @Autowired
        @Qualifier("serviceConnectionFactory")
        public ConnectionFactory connectionFactory;

        @Autowired
        @Qualifier("serviceTaskExecuter")
        private Executor taskExecutor;


        @Override
        public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        }

        @Bean(name = "rabbitListenerContainerFactory")
        public ExtendedRabbitListenerContainerFactory rabbitListenerContainerFactory()
        {
                ExtendedRabbitListenerContainerFactory factory = new ExtendedRabbitListenerContainerFactory();
                factory.setConcurrentConsumers(10);
                factory.setMaxConcurrentConsumers(10);
                factory.setTaskExecutor(taskExecutor);
                factory.setConnectionFactory(connectionFactory);
                factory.setMessageConverter(jackson2JsonMessageConverter());
                factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
                factory.setAutoStartup(true);
                factory.setAutoDeclare(true);
                return factory;
        }

        @Bean
        public Queue locationCommandQueue()
        {
                log.debug("locationCommandQueue definition [admin=" + serviceAdmin.toString() + "]");

                HashMap<String, Object> arguments = new HashMap<>();
                arguments.put("x-message-ttl", (int) 5000);
                Queue queue = new Queue(activityCommandQueueName, true, false, false, arguments);
                queue.setAdminsThatShouldDeclare(serviceAdmin);
                queue.setShouldDeclare(true);
                return queue;
        }

        @Bean
        public org.springframework.amqp.core.Exchange locationCommandExchange()
        {
                return ExchangeBuilder.topicExchange(activityCommandExchangeName).autoDelete().build();
        }

        @Bean
        public org.springframework.amqp.core.Exchange locationTrackingExchange()
        {
                return ExchangeBuilder.topicExchange(activityTrackingExchangeName).autoDelete().build();
        }

        @Bean
        public Binding locationCommandBinding()
        {
                return BindingBuilder.bind(locationCommandQueue()).to(locationCommandExchange()).with(activityCommandsRoutingKey).noargs();
        }

        @Bean
        public Queue locationTrackingQueue()
        {
                HashMap<String, Object> arguments = new HashMap<>();
                arguments.put("x-max-length-bytes", (int) 50 * 1024 * 1024);
                Queue queue = new Queue(activityTrackingQueueName, true, false, false, arguments);
                queue.setAdminsThatShouldDeclare(serviceAdmin);
                queue.setShouldDeclare(true);
                return queue;
        }

        @Bean
        public Binding locationTrackingBinding()
        {
                return BindingBuilder.bind(locationTrackingQueue()).to(locationTrackingExchange()).with(activityTrackingUpdateRoutingKey).noargs();
        }

        @Bean
        public Jackson2JsonMessageConverter jackson2JsonMessageConverter()
        {
                return new Jackson2JsonMessageConverter();
        }
}
