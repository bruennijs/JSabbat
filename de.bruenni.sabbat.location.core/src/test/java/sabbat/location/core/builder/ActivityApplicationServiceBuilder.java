package sabbat.location.core.builder;

import identity.IAuthenticationService;
import infrastructure.identity.AuthenticationFailedException;
import sabbat.location.core.application.service.ActivityApplicationService;
import sabbat.location.core.persistence.activity.IActivityRepository;

/**
 * Created by bruenni on 01.10.16.
 */
public class ActivityApplicationServiceBuilder {


    private IActivityRepository repository = new ActivityRepositoryBuilder().buildmocked();
    private identity.IAuthenticationService authenticationService = new AuthenticationServiceBuilder().buildMocked();

    public ActivityApplicationServiceBuilder() throws AuthenticationFailedException {
    }

    public ActivityApplicationService build() {
        return new ActivityApplicationService(this.repository, this.authenticationService);
    }

    public ActivityApplicationServiceBuilder withAuthenticationService(IAuthenticationService value) {
        this.authenticationService = value;
        return this;
    }
}
