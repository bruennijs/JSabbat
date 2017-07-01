package sabbat.location.infrastructure.integrationtest;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import sabbat.location.core.persistence.activity.IActivityRepository;
import sabbat.location.infrastructure.JpaAutoConfiguration;
import sabbat.location.infrastructure.LocationInfrastructureConfiguration;
import sabbat.location.infrastructure.LocationInfrastructureDevConfiguration;
import sabbat.location.infrastructure.persistence.activity.JpaActivityRepository;

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
@ImportAutoConfiguration(value = {JpaAutoConfiguration.class, LocationInfrastructureConfiguration.class, LocationInfrastructureDevConfiguration.class})
public class IntegrationTestConfig {
        @Bean
        public PropertySourcesPlaceholderConfigurer propertySource()
        {
                return new PropertySourcesPlaceholderConfigurer();
        }
}
