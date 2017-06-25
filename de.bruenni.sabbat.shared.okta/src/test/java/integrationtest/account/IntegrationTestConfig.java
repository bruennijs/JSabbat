package integrationtest.account;

import configuration.SabbatSharedOktaAutoConfiguration;
import configuration.CacheConfiguration;
import configuration.OktaApiConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by bruenni on 16.10.16.
 */
@Configuration
@Import( { SabbatSharedOktaAutoConfiguration.class, OktaApiConfiguration.class, CacheConfiguration.class})
public class IntegrationTestConfig {
    @Bean
    public PropertySourcesPlaceholderConfigurer propertySource()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
}