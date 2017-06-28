package integrationtest;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import sabbat.location.api.AmqpClientAutoConfiguration;

/**
 * Created by bruenni on 28.06.17.
 */
@Configuration
@ImportAutoConfiguration(value = {AmqpClientAutoConfiguration.class})
public class IntegrationTestConfig {
    @Bean
    public PropertySourcesPlaceholderConfigurer propertySource()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
