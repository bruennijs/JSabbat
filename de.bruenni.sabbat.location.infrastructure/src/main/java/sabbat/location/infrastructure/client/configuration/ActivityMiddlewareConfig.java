package sabbat.location.infrastructure.client.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.config.EnableIntegration;

/**
 * Created by bruenni on 05.08.16.
 */
@Configuration
@ImportResource(locations = {"classpath:spring/spring-location-infrastructure.xml"})
//@EnableIntegration
public class ActivityMiddlewareConfig {
}
