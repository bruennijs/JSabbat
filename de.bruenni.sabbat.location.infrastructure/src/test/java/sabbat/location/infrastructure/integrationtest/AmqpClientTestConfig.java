package sabbat.location.infrastructure.integrationtest;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by bruenni on 13.07.16.
 */
@Configuration
@PropertySource("classpath:test/application.properties")
@ImportResource(locations =
        {
                "classpath:test/spring-location-infrastructure-test.xml",
                "classpath:spring/spring-location-integration.xml",
                "classpath:spring/spring-location-amqp-client.xml"
        })
public class AmqpClientTestConfig {
}
