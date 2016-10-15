package identity.implementation;

import identity.GroupRef;
import identity.IAuthenticationService;
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
import java.util.stream.Collectors;

/**
 * Created by bruenni on 15.10.16.
 */
public class JwtAuthenticationService implements IAuthenticationService {

    public static final String USERNAME = "username";
    private static final String GROUPS = "groups";
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

        String userName = (String)jwt.getClaims().get(USERNAME);
        String groups = (String)jwt.getClaims().get(GROUPS);

        return new UserRef(userName, userName, Arrays.stream(groups.split("\\ *,\\ *")).map(GroupRef::new).collect(Collectors.toList()));
    }

    @Override
    public Token authenticate(String userName, String password) {

        Instant iat = Instant.now(Clock.systemUTC());
        Instant exp = iat.plus(7, ChronoUnit.DAYS);

        HashMap<String, Object> claims = new HashMap<>();
        claims.put(USERNAME, userName);
        claims.put(GROUPS, "users");

        Jwt jwt = new Jwt(userName, Date.from(iat), Date.from(exp), claims);

        return this.tokenAuthentication.create(jwt);
    }
}
