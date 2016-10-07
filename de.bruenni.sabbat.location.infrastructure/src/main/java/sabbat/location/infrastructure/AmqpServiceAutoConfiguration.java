package sabbat.location.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;

import java.util.HashMap;

/**
 * Created by bruenni on 24.09.16.
 */
@Configuration
@ConditionalOnProperty(prefix = "location.infrastructure.amqp.service",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = false)
@PropertySource("classpath:sabbat-location-infrastructure.properties")
@ImportResource(locations =
        {
                "classpath:spring/spring-location-infrastructure.xml",
                "classpath:spring/spring-location-amqp-service.xml"
        })
@EnableRabbit   //IMPORT: to use @RabbitListener annotated classes -> Moreover a SimpleMessageListenerContainerFactory can be registered to customize created container
public class AmqpServiceAutoConfiguration implements RabbitListenerConfigurer {

        private Logger log = LoggerFactory.getLogger(AmqpServiceAutoConfiguration.class);

        @Value("${location.activity.queue.command}")
        public String activityCommandQueueName;

        @Value("${location.activity.queue.command.reply}")
        public String activityCommandQueueReplyName;

        @Value("${location.activity.exchange.command}")
        public String activityCommandExchangeName;

        @Value("${location.activity.routingkey.command.all}")
        public String activityCommandsRoutingKey;

        @Autowired
        @Qualifier("serviceAdmin")
        public RabbitAdmin serviceAdmin;


        @Override
        public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        }

/*        <!--    <rabbit:queue id="activityCommandQueue"
        name="${location.activity.queue.command}"
        declared-by="serviceAdmin"
        auto-declare="true"
        durable="true"/>

    <rabbit:queue id="activityCommandReplyQueue"
        name="${location.activity.queue.command.reply}"
        declared-by="serviceAdmin"
        auto-declare="true"
        durable="true" />-->
        */
        @Bean
        public Queue locationCommandQueue()
        {
                log.debug("locationCommandQueue definition [admin=" + serviceAdmin.toString() + "]");

                HashMap<String, Object> arguments = new HashMap<>();
                arguments.put("x-message-ttl", (int) 5000);
                Queue queue = new Queue(activityCommandQueueName, true, false, false, arguments);
                queue.setAdminsThatShouldDeclare(serviceAdmin);
                queue.shouldDeclare();
                return queue;
        }

        @Bean
        public Queue locationCommandreplyQueue()
        {
                log.debug("locationCommandreplyQueue definition [admin=" + serviceAdmin.toString() + "]");

                HashMap<String, Object> arguments = new HashMap<>();
                Queue queue = new Queue(activityCommandQueueReplyName, true, false, false, arguments);
                queue.setAdminsThatShouldDeclare(serviceAdmin);
                queue.shouldDeclare();
                return queue;
        }

        @Bean
        public org.springframework.amqp.core.Exchange locationActivityExchangeCommand()
        {
                return ExchangeBuilder.topicExchange(activityCommandExchangeName).durable().build();
        }

        @Bean
        public Binding locationCommandBinding()
        {
                return BindingBuilder.bind(locationCommandQueue()).to(locationActivityExchangeCommand()).with(activityCommandsRoutingKey).noargs();
        }
}
