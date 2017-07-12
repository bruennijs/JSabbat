package spring.security;

import identity.UserJwtBuilder;
import infrastructure.identity.ITokenAuthentication;
import infrastructure.identity.Jwt;
import infrastructure.identity.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by bruenni on 30.10.16.
 * Accepts: UsernamePasswordAuthenticationToken
 * This implemenation delagets to AuthenticationProvider and returns Authentication impl of type
 * '' where JWT token issued by
 *
 * Issues
 */
public class SabbatJwtAuthenticationProvider implements AuthenticationProvider {

    @Value("${sabbat.shared.jwt.ttl}")
    public long JwtTtl = 3600;

    private ITokenAuthentication tokenAuthentication;
    private AuthenticationProvider authenticationProvider;

    /**
     * Constructor
     * @param tokenAuthentication
     * @param authenticationProvider
     */
    public SabbatJwtAuthenticationProvider(ITokenAuthentication tokenAuthentication,
                                           AuthenticationProvider authenticationProvider) {
        this.tokenAuthentication = tokenAuthentication;
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Authentication providerAuthentication = this.authenticationProvider.authenticate(authentication);

        if (!(providerAuthentication instanceof UsernamePasswordAuthenticationToken))
            throw new BadAuthenticationTypeException("delegated userAuthentication is not of type 'UsernamePasswordAuthenticationToken'");

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken)providerAuthentication;
        if (!(usernamePasswordAuthenticationToken.getPrincipal() instanceof User))
        {
            throw new BadAuthenticationTypeException("usernamePasswordAuthenticationToken getprincipal is not of type 'User'");
        }

        Object principal = usernamePasswordAuthenticationToken.getPrincipal();

        Token jwtToken = createJwtTokenFromUser(principal);

        //// add JwtToken as details
        usernamePasswordAuthenticationToken.setDetails(jwtToken);

        return usernamePasswordAuthenticationToken;
    }

    private Token createJwtTokenFromUser(Object principal) throws AuthenticationException {
        if (principal instanceof UserExtended)
        {
            // own AuthenticationProvider can provide these special extended UserDetail object to provide
            // special data from remote authentications services like IDs
            // OKTA and InMemory variants are used with UserExtended
            return buildJwtFromUserDetails((UserExtended)principal, user -> user.getId());
        } else if (principal instanceof User)
        {
            // many AuthenticationProvider instantiate these objects , then use username as id

            //// create JWT with user properties
            return buildJwtFromUserDetails((User)principal, user -> user.getUsername());
        }

        throw new BadAuthenticationTypeException("principal type not supported");
    }

    private <TUser extends User> Token buildJwtFromUserDetails(TUser userDetails, Function<? super TUser, String> userIdGetter) {

        Instant now = Instant.now(Clock.systemUTC());

        List<String> authorities = userDetails.getAuthorities().stream().map(authority ->
        {
            return authority.getAuthority();
        }).collect(Collectors.toList());

        Jwt jwt = new UserJwtBuilder().withData(userIdGetter.apply(userDetails),
                                                authorities,
                                                now,
                                                now.plusSeconds(this.JwtTtl))
                                        .build();

        //// create JWT token
        return this.tokenAuthentication.create(jwt);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        if (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication)) return true;
        return false;
    }
}
