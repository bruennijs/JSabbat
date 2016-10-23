package sabbat.location.infrastructure.builder;

import infrastructure.identity.ITokenAuthentication;
import infrastructure.identity.implementation.JJwtTokenAuthentication;

/**
 * Created by bruenni on 23.10.16.
 */
public class TokenAuthenticationBuilder {
    public static ITokenAuthentication build() {
        return new JJwtTokenAuthentication();
    }
}
