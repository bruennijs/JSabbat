package sabbat.apigateway.location.unittest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by bruenni on 13.07.16.
 */
@Configuration
@PropertySource("classpath:test/application.properties")
@ImportResource("classpath:test/spring-api-gateway-unittest.xml")
public class UnitTestConfig {
/*    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }*/
}
