package builder;

import infrastructure.identity.implementation.JJwtTokenAuthentication;

/**
 * Created by bruenni on 16.10.16.
 */
public class JJwtTokenAuthenticationBuilder {
    public JJwtTokenAuthentication build() {
        return new JJwtTokenAuthentication();
    }
}
