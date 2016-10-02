package sabbat.location.infrastructure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by bruenni on 24.09.16.
 */
@Configuration
//@ConditionalOnProperty(prefix = "location.infrastructure.cassandra", name = "enabled", havingValue = "false", matchIfMissing = true)
@ConditionalOnProperty(prefix = "location.infrastructure.cassandra", name = "enabled", havingValue = "true", matchIfMissing = false)
@PropertySource("classpath:sabbat-location-infrastructure.properties")
@ImportResource(locations =
        {
                "classpath:spring/spring-location-infrastructure.xml",
                //"classpath:spring/spring-location-amqp-client.xml",
                "classpath:spring/spring-location-cassandra.xml"
        })
public class CassandraAutoConfiguration {
}
