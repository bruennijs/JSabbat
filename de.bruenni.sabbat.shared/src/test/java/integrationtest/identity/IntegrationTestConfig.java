package integrationtest.identity;

import org.springframework.boot.autoconfigure.test.ImportAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import sabbat.shared.SabbatSharedAutoConfiguration;

/**
 * Created by bruenni on 16.10.16.
 */
@Configuration
@PropertySource(value=
        {
                "classpath:/sabbat/shared/default.properties",
                "classpath:application.properties"
        })
@Import(SabbatSharedAutoConfiguration.class)
public class IntegrationTestConfig {
    @Bean
    public PropertySourcesPlaceholderConfigurer propertySource()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
