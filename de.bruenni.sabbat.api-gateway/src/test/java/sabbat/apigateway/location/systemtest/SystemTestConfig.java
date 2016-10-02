package sabbat.apigateway.location.systemtest;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by bruenni on 13.07.16.
 */
@Configuration
@PropertySource("classpath:test/application.properties")
@ImportResource(locations = {"classpath:test/spring-api-gateway-test.xml"})
public class SystemTestConfig {
}
