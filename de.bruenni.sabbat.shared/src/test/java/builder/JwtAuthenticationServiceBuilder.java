package builder;

import identity.implementation.JwtAuthenticationService;
import infrastructure.identity.ITokenAuthentication;

/**
 * Created by bruenni on 15.10.16.
 */
public class JwtAuthenticationServiceBuilder {
    private ITokenAuthentication tokenService = new JJwtTokenAuthenticationBuilder().build();

    public JwtAuthenticationService build() {
        return new JwtAuthenticationService(this.tokenService);
    }
}
