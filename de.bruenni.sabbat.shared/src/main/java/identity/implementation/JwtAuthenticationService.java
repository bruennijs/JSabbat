package identity.implementation;

import identity.GroupRef;
import identity.IAuthenticationService;
import identity.UserJwtBuilder;
import identity.UserRef;
import infrastructure.identity.AuthenticationFailedException;
import infrastructure.identity.ITokenAuthentication;
import infrastructure.identity.Jwt;
import infrastructure.identity.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bruenni on 15.10.16.
 */
public class JwtAuthenticationService implements IAuthenticationService {

    private static Logger logger = LoggerFactory.getLogger(JwtAuthenticationService.class);

    private ITokenAuthentication tokenAuthentication;

    /**
     * Constructor
     * @param tokenAuthentication
     */
    public JwtAuthenticationService(ITokenAuthentication tokenAuthentication) {
        this.tokenAuthentication = tokenAuthentication;
    }

    @Override
    public UserRef verify(Token token) throws AuthenticationFailedException {

        if (logger.isDebugEnabled())
            logger.debug(String.format("Verifying token [%1s]", token.getValue()));

        Jwt jwt = this.tokenAuthentication.verify(token);

        String userId = new UserJwtBuilder().fromJwt(jwt).getUserId();
        List<String> groupNames = new UserJwtBuilder().fromJwt(jwt).getGroupNames();

        return new UserRef(userId, groupNames.stream().map(GroupRef::new).collect(Collectors.toList()));
    }

    @Override
    public Token authenticate(String userName, String password) throws Exception {
        throw new Exception("cannot be implemented cause can only verify tokens.");
    }

    public Token create(String userId) {

        Instant iat = Instant.now(Clock.systemUTC());
        Instant exp = iat.plus(3, ChronoUnit.DAYS);

        Jwt jwt = new UserJwtBuilder().withData(userId, Arrays.asList("users"), iat, exp).build();

        return this.tokenAuthentication.create(jwt);
    }
}
