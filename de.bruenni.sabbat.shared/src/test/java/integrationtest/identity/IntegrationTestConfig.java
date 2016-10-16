package integrationtest.identity;

import org.springframework.boot.autoconfigure.test.ImportAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by bruenni on 16.10.16.
 */
@Configuration
@PropertySource(value= {"classpath:application.properties"})
public class IntegrationTestConfig {
    @Bean
    public PropertySourcesPlaceholderConfigurer propertySource()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
