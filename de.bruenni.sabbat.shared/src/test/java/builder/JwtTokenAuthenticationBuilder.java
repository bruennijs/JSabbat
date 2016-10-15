package builder;

import infrastructure.identity.ITokenAuthentication;
import infrastructure.identity.implementation.JJwtTokenAuthentication;

/**
 * Created by bruenni on 15.10.16.
 */
public class JwtTokenAuthenticationBuilder {
    public JJwtTokenAuthentication build() {
        return new JJwtTokenAuthentication();
    }
}
