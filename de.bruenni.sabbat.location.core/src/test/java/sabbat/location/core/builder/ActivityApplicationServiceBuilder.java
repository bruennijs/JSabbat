package sabbat.location.core.builder;

import identity.IAuthenticationService;
import infrastructure.common.event.IDomainEventBus;
import infrastructure.common.event.implementation.DomainEventBusImpl;
import infrastructure.identity.AuthenticationFailedException;
import sabbat.location.core.application.service.implementation.DefaultActivityApplicationService;
import sabbat.location.core.persistence.activity.IActivityRepository;

import static org.mockito.Mockito.mock;

/**
 * Created by bruenni on 01.10.16.
 */
public class ActivityApplicationServiceBuilder {


    private IActivityRepository repository = new ActivityRepositoryBuilder().buildmocked();
    private identity.IAuthenticationService authenticationService = new AuthenticationServiceBuilder().buildMocked();
    private IDomainEventBus domainEventBus = new DomainEventBusImpl();

    public ActivityApplicationServiceBuilder() throws AuthenticationFailedException {
    }

    public DefaultActivityApplicationService build() {
        return new DefaultActivityApplicationService(this.repository, this.authenticationService, this.domainEventBus);
    }

    public ActivityApplicationServiceBuilder withAuthenticationService(IAuthenticationService value) {
        this.authenticationService = value;
        return this;
    }
}
