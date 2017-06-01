package sabbat.location.core.builder;

import identity.IAuthenticationService;
import infrastructure.identity.AuthenticationFailedException;
import org.springframework.context.ApplicationEventPublisher;
import sabbat.location.core.application.service.implementation.DefaultActivityApplicationService;
import sabbat.location.core.builder.org.springframework.context.ApplicationEventPublisherBuilder;
import sabbat.location.core.persistence.activity.IActivityRepository;

import static org.mockito.Mockito.mock;

/**
 * Created by bruenni on 01.10.16.
 */
public class ActivityApplicationServiceBuilder {


    private IActivityRepository repository = new ActivityRepositoryBuilder().buildmocked();
    private identity.IAuthenticationService authenticationService = new AuthenticationServiceBuilder().buildMocked();
    private ApplicationEventPublisher applicationEventPublisherMock = new ApplicationEventPublisherBuilder().buildMocked();

    public ActivityApplicationServiceBuilder() throws AuthenticationFailedException {
    }

    public DefaultActivityApplicationService build() {
        DefaultActivityApplicationService instance = new DefaultActivityApplicationService(this.repository, this.authenticationService);
        instance.setApplicationEventPublisher(applicationEventPublisherMock);
        return instance;
    }

    public ActivityApplicationServiceBuilder withAuthenticationService(IAuthenticationService value) {
        this.authenticationService = value;
        return this;
    }
}
