package builder;

import identity.implementation.StormpathAuthenticationService;
import infrastructure.identity.ITokenAuthentication;

/**
 * Created by bruenni on 16.10.16.
 */
public class StormpathAuthenticationServiceBuilder {
    private ITokenAuthentication tokenAuthentication = new JJwtTokenAuthenticationBuilder().build();
    private String appName = "";

    public StormpathAuthenticationService build() {
        StormpathAuthenticationService instance = new StormpathAuthenticationService(this.tokenAuthentication);
        instance.ApplicationName = this.appName;
        return instance;
    }

    public StormpathAuthenticationServiceBuilder withApplicationName(String value) {
        this.appName = value;
        return this;
    }

    public StormpathAuthenticationServiceBuilder withTokenAuthentication(ITokenAuthentication value) {
        this.tokenAuthentication = value;
        return this;
    }
}
