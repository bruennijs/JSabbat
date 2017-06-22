package sabbat.location.core;

import account.IAccountService;
import identity.IAuthenticationService;
import infrastructure.common.event.IDomainEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;
import sabbat.location.core.application.service.GroupActivityApplicationService;
import sabbat.location.core.application.service.implementation.DefaultActivityApplicationService;
import sabbat.location.core.application.service.ActivityApplicationService;
import sabbat.location.core.application.service.implementation.DefaultGroupActivityApplicationService;
import sabbat.location.core.domain.service.GroupActivityDomainService;
import sabbat.location.core.domain.service.implementation.DefaultGroupActivityDomainService;
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

        @Bean(name = "activityRepository")
        @ConditionalOnMissingBean(IActivityRepository.class)
        public IActivityRepository activityRepository()
        {
                return new ActivityRepositoryDummy();
        }

        @Autowired()
        public IAccountService accountService;

        @Bean(name = "activityApplicationService")
        @ConditionalOnMissingBean(ActivityApplicationService.class)
        @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
        public ActivityApplicationService activityApplicationService(IActivityRepository activityRepository)
        {
                return new DefaultActivityApplicationService(activityRepository, this.authenticationService);
        }

        @Bean(name = "groupActivityDomainService")
        @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
        public GroupActivityDomainService groupActivityDomainService()
        {
                return new DefaultGroupActivityDomainService(this.activityRepository(), this.accountService);
        }

        @Bean(name = "groupActivityApplicationService")
        @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
        public GroupActivityApplicationService groupActivityApplicationService()
        {
                return new DefaultGroupActivityApplicationService(this.authenticationService, groupActivityDomainService());
        }
}
