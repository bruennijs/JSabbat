package identity.implementation;

import identity.GroupRef;
import identity.IAuthenticationService;
import identity.UserJwtBuilder;
import identity.UserRef;
import infrastructure.identity.AuthenticationFailedException;
import infrastructure.identity.ITokenAuthentication;
import infrastructure.identity.Jwt;
import infrastructure.identity.Token;
import infrastructure.util.IterableUtils;
import infrastructure.util.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.HashMap;
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

        String userName = new UserJwtBuilder().fromJwt(jwt).getUserName();
        List<String> groupNames = new UserJwtBuilder().fromJwt(jwt).getGroupNames();

        return new UserRef(userName, userName, groupNames.stream().map(GroupRef::new).collect(Collectors.toList()));
    }

    @Override
    public Token authenticate(String userName, String password) {

        Instant iat = Instant.now(Clock.systemUTC());
        Instant exp = iat.plus(3, ChronoUnit.DAYS);

        Jwt jwt = new UserJwtBuilder().withData(userName, Arrays.asList("users"), iat, exp).build();

        return this.tokenAuthentication.create(jwt);
    }
}
