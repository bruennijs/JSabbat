package sabbat.location.infrastructure;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.rabbit.listener.adapter.MessagingMessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessagingMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;

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

        @Override
        public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        }
}
