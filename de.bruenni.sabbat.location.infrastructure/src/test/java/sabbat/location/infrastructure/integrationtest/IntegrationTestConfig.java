package sabbat.location.infrastructure.integrationtest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import sabbat.location.core.persistence.activity.IActivityRepository;
import sabbat.location.infrastructure.persistence.activity.JpaActivityRepository;

/**
 * Created by bruenni on 13.07.16.
 */
@Configuration
@PropertySource(
        {
                "classpath:sabbat-location-infrastructure.properties",
                "classpath:application.properties"
        })
@ImportResource(locations =
        {
                "classpath:test/spring-location-infrastructure-test.xml",
                "classpath:spring/spring-location-integration.xml"
        })
public class IntegrationTestConfig {

        @Bean(name = "activityRepository")
        public IActivityRepository activityRepository()
        {
                return new JpaActivityRepository();
        }

}
