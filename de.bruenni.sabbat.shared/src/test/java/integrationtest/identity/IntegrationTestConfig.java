package integrationtest.identity;

import configuration.SabbatSharedAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by bruenni on 16.10.16.
 */
@Configuration
@Import(value = {SabbatSharedAutoConfiguration.class})
public class IntegrationTestConfig {
    @Bean
    public PropertySourcesPlaceholderConfigurer propertySource()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
