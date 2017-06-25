package sabbat.location.infrastructure;

import infrastructure.parser.JsonDtoParser;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

/**
 * Created by bruenni on 05.08.16.
 */
@Configuration
@Profile({"dev"})
@PropertySource(value = "classpath:sabbat-location-infrastructure-dev.properties", ignoreResourceNotFound = true)
public class LocationInfrastructureDevConfiguration {
}
