package integrationtest.account;

import configuration.SabbatSharedOktaAutoConfiguration;
import configuration.CacheConfiguration;
import configuration.OktaApiConfiguration;
import configuration.SabbatSharedOktaDevAutoConfiguration;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by bruenni on 16.10.16.
 */
@Configuration
@ImportAutoConfiguration(
        {
                SabbatSharedOktaDevAutoConfiguration.class,
                SabbatSharedOktaAutoConfiguration.class,
                OktaApiConfiguration.class,
                CacheConfiguration.class
        })
public class IntegrationTestConfig {
    @Bean
    public PropertySourcesPlaceholderConfigurer propertySource()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
