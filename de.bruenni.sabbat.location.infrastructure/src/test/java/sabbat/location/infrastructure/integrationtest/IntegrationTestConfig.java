package sabbat.location.infrastructure.integrationtest;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
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
        @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
        public IActivityRepository activityRepository()
        {
                return new JpaActivityRepository();
        }

}
