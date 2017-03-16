package sabbat.location.core;

import identity.IAuthenticationService;
import infrastructure.common.event.IDomainEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;
import sabbat.location.core.application.service.implementation.DefaultActivityApplicationService;
import sabbat.location.core.application.service.IActivityApplicationService;
import sabbat.location.core.persistence.activity.IActivityRepository;
import sabbat.location.core.persistence.activity.implementation.ActivityRepositoryDummy;

/**
 * Created by bruenni on 25.09.16.
 */
@Configuration
@ConditionalOnProperty(prefix = "location.core", value = "enabled", havingValue = "true", matchIfMissing = false)
@PropertySource("classpath:spring/spring-location-core.properties")
@ImportResource(locations =
        {
                "classpath:spring/spring-location-core.xml"
        })
public class LocationCoreConfiguration {

        @Autowired()
        @Qualifier("verifyingAuthenticationService")
        public IAuthenticationService authenticationService;

        @Autowired
        @Qualifier("DomainEventBus")
        private IDomainEventBus domainEventBus;

        @Bean(name = "activityRepository")
        @ConditionalOnMissingBean(IActivityRepository.class)
        public IActivityRepository activityRepository()
        {
                return new ActivityRepositoryDummy();
        }

        @Bean(name = "activityApplicationService")
        @ConditionalOnMissingBean(IActivityApplicationService.class)
        @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
        public IActivityApplicationService activityApplicationService(IActivityRepository activityRepository)
        {
                return new DefaultActivityApplicationService(activityRepository, this.authenticationService, this.domainEventBus);
        }
}
