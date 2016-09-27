package sabbat.location.infrastructure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by bruenni on 24.09.16.
 */
@Configuration
@ConditionalOnProperty(prefix = "location.infrastructure.amqp.service",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true)
@PropertySource("classpath:sabbat-location-infrastructure.properties")
@ImportResource(locations =
        {
                "classpath:spring/spring-location-infrastructure.xml",
                "classpath:spring/spring-location-amqp-service.xml"
        })
public class AmqpServiceAutoConfiguration {
}
