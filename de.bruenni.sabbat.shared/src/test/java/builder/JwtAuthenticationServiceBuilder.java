package builder;

import identity.implementation.JwtAuthenticationService;
import infrastructure.identity.ITokenAuthentication;
import infrastructure.identity.implementation.JJwtTokenAuthentication;

/**
 * Created by bruenni on 15.10.16.
 */
public class JwtAuthenticationServiceBuilder {
    private ITokenAuthentication tokenService = new JJwtTokenAuthentication();

    public JwtAuthenticationService build() {
        return new JwtAuthenticationService(this.tokenService);
    }
}
