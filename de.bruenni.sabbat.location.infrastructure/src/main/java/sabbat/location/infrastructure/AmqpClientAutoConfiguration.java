package sabbat.location.infrastructure;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;

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

        @Value("${location.activity.routingkey.tracking.update}")
        public String trackingUpdateRoutingKey;

        @Value("${location.activity.exchange.tracking}")
        public String trackingExchangeName;

        @Autowired
        @Qualifier("clientConnectionFactory")
        public ConnectionFactory connectionFactory;

        @Bean(name = "locationUpdateRabbitTemplate")
        public RabbitTemplate locationUpdateRabbitTemplate() {
                RabbitTemplate template = new RabbitTemplate(connectionFactory);
                template.setExchange(trackingExchangeName);
                template.setRoutingKey(trackingUpdateRoutingKey);
                return template;
        }
}
