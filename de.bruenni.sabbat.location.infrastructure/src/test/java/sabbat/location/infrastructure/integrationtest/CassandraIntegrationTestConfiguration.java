package sabbat.location.infrastructure.integrationtest;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import sabbat.location.infrastructure.CassandraAutoConfiguration;
import sabbat.location.infrastructure.LocationInfrastructureConfiguration;

/**
 * Created by bruenni on 07.07.17.
 */
@Configuration
@TestPropertySource(properties = "location.infrastructure.cassandra.enabled=true")
@ImportAutoConfiguration(value = {CassandraAutoConfiguration.class, LocationInfrastructureConfiguration.class})
public class CassandraIntegrationTestConfiguration {
}
