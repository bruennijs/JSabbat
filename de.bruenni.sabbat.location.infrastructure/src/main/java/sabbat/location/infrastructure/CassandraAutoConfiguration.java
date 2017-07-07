package sabbat.location.infrastructure;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;
import org.springframework.data.cassandra.core.CassandraTemplate;
import sabbat.location.core.application.service.MaesurementApplicationService;
import sabbat.location.core.application.service.implementation.DefaultMeasurementApplicationService;
import sabbat.location.core.persistence.coordinate.UserCoordinateRepository;
import sabbat.location.infrastructure.persistence.measurement.CassandraUserCoordinateRepository;

/**
 * Created by bruenni on 24.09.16.
 */
@Configuration
//@ConditionalOnProperty(prefix = "location.infrastructure.cassandra", name = "enabled", havingValue = "true", matchIfMissing = false)
@Import(LocationInfrastructureConfiguration.class)
@ImportResource(locations =
        {
                "classpath:spring/spring-location-cassandra.xml"
        })
public class CassandraAutoConfiguration {

        @Bean(name = "cassandraUserCoordinateRepository")
        @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
        public UserCoordinateRepository userCoordinateRepository(CassandraTemplate cassandraTemplate)
        {
            return new CassandraUserCoordinateRepository(cassandraTemplate);
        }

        @Bean(name = "defaultMeasurementApplicationService")
        @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
        public MaesurementApplicationService measurementApplicationService(UserCoordinateRepository userCoordinateRepository)
        {
            return new DefaultMeasurementApplicationService(userCoordinateRepository);
        }
}
