package sabbat.location.infrastructure.integrationtest;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import sabbat.location.infrastructure.JpaAutoConfiguration;
import sabbat.location.infrastructure.LocationInfrastructureConfiguration;

/**
 * Created by bruenni on 13.07.16.
 */
@Configuration
@PropertySource(
        {
                "classpath:sabbat-location-infrastructure.properties",
                //"classpath:application.properties"
        })
@ImportResource(locations =
        {
                "classpath:test/spring-location-infrastructure-test.xml",
                "classpath:spring/spring-location-integration.xml"
        })
@ImportAutoConfiguration(value = {JpaAutoConfiguration.class, LocationInfrastructureConfiguration.class})
public class IntegrationTestConfig {
        @Bean
        public PropertySourcesPlaceholderConfigurer propertySource()
        {
                return new PropertySourcesPlaceholderConfigurer();
        }
}
