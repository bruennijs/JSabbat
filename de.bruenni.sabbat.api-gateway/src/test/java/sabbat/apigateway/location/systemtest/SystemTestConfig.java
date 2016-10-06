package sabbat.apigateway.location.systemtest;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by bruenni on 13.07.16.
 */
@Configuration
@Profile("test")
@ActiveProfiles(value = "test")
/*@SpringBootApplication(exclude =
        {
                CassandraAutoConfiguration.class,
                CassandraDataAutoConfiguration.class,
                RabbitAutoConfiguration.class,
        })*/
//@PropertySource("classpath:application.properties")
@ImportResource("classpath:test/spring-api-gateway-test.xml")
public class SystemTestConfig {
}
