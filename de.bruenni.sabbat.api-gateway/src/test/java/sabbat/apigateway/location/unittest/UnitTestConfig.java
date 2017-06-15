package sabbat.apigateway.location.unittest;

import org.springframework.context.annotation.*;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by bruenni on 13.07.16.
 */
@Configuration
@Profile("test")
//@ActiveProfiles(value = "test")
//@Import(value = UnitTestBaseConfig.class)
//@PropertySource("classpath:test/application-test.properties")
@ImportResource("classpath:test/spring-api-gateway-test.xml")
public class UnitTestConfig {
}
