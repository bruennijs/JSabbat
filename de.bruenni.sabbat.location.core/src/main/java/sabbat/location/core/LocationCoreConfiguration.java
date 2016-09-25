package sabbat.location.core;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by bruenni on 25.09.16.
 */
@Configuration
@PropertySource("classpath:spring/spring-location-core.properties")
@ImportResource(locations =
        {
                "classpath:spring/spring-location-core.xml"
        })
public class LocationCoreConfiguration {
}
