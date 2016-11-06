package sabbat.location.infrastructure.builder;

import identity.UserJwtBuilder;
import infrastructure.identity.ITokenAuthentication;
import infrastructure.identity.Jwt;
import infrastructure.identity.Token;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bruenni on 23.10.16.
 */
public class ActivityRequestDtoBuilderBase {
    protected final Token identityToken;
    protected ITokenAuthentication tokenAuthentication = TokenAuthenticationBuilder.build();

    public ActivityRequestDtoBuilderBase() {

        Jwt jwt = new UserJwtBuilder().withData("username.infrastructure.test", Arrays.asList("group1.infrastructure.test"), Instant.now(), Instant.now().plusSeconds(3600)).build();

        this.identityToken = tokenAuthentication.create(jwt);
    }
}
