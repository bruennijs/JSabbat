package builder;

import identity.UserJwtBuilder;
import infrastructure.identity.ITokenAuthentication;
import infrastructure.identity.Jwt;

import java.time.Instant;
import java.util.Arrays;

/**
 * Created by bruenni on 23.10.16.
 */
public class ActivityRequestDtoBuilderBase<TBuilder> {
    protected String activityId = "activity:id";
    protected String identityToken;
    protected ITokenAuthentication tokenAuthentication = TokenAuthenticationBuilder.build();

    public ActivityRequestDtoBuilderBase() {

        Jwt jwt = new UserJwtBuilder().withData("username.infrastructure.test", Arrays.asList("group1.infrastructure.test"), Instant.now(), Instant.now().plusSeconds(3600)).build();

        this.identityToken = tokenAuthentication.create(jwt).getValue();
    }

    public TBuilder WithIdentityToken(String value) {
        this.identityToken = value;
        return (TBuilder)this;
    }

    public TBuilder withActivityId(String value) {
        this.activityId = value;
        return (TBuilder)this;
    }
}
