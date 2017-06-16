package sabbat.apigateway.location.integrationtest;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by bruenni on 13.07.16.
 */
@Configuration
@Profile("test")
//@PropertySource("classpath:test/application-test.properties")
@ImportResource(locations = {"classpath:test/spring-api-gateway-test.xml"})
public class IntegrationTestConfig {

}
