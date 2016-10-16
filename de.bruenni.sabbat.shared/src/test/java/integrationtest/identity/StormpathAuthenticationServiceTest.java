package integrationtest.identity;

import builder.JJwtTokenAuthenticationBuilder;
import builder.JwtAuthenticationServiceBuilder;
import builder.JwtTokenAuthenticationBuilder;
import builder.StormpathAuthenticationServiceBuilder;
import identity.UserRef;
import identity.implementation.JwtAuthenticationService;
import identity.implementation.StormpathAuthenticationService;
import infrastructure.identity.AuthenticationFailedException;
import infrastructure.identity.ITokenAuthentication;
import infrastructure.identity.Jwt;
import infrastructure.identity.Token;
import infrastructure.identity.implementation.JJwtTokenAuthentication;
import infrastructure.identity.implementation.JJwtTokenAuthenticationFactory;
import infrastructure.util.StreamUtils;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.core.IsCollectionContaining;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by bruenni on 15.10.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IntegrationTestConfig.class)
public class StormpathAuthenticationServiceTest {
    @Value("${stormpath.application.name}")
    public String applicationName;


    @Test
    public void When_authenticate_expect_JWT_token_parsed_contains_username_and_groups() throws Exception {
        List<String> expectedGroupNames = Arrays.asList("oldenburgerradsportfreunde", "all");
        String userName = "bruenni";

        ITokenAuthentication tokenAuthentication = new JJwtTokenAuthenticationBuilder().build();
        StormpathAuthenticationService sut = new StormpathAuthenticationServiceBuilder()
                .withTokenAuthentication(tokenAuthentication)
                .withApplicationName(applicationName)
                .build();
        Token token = sut.authenticate(userName, "bruenni2016");

        Jwt jwt = tokenAuthentication.verify(token);
        Assert.assertEquals(userName, jwt.getClaims().get("username"));
    }

    @Test
    public void When_create_from_userref_expect_verify_succeeds_with_equal_userref_parsed() throws Exception {
        String userName = "bruenni";

        StormpathAuthenticationService sut = new StormpathAuthenticationServiceBuilder().withApplicationName(applicationName).build();
        Token token = sut.authenticate(userName, "bruenni2016");
        UserRef userRef = sut.verify(token);

        Assert.assertEquals(userName, userRef.getName());
        List<String> actualGroupNames = userRef.getGroups().stream().map(gref -> {
            return gref.getName();
        }).collect(Collectors.toList());

        Assert.assertThat(
                actualGroupNames,
                IsCollectionContaining.hasItems("oldenburgerradsportfreunde", "all"));
    }

    @Test
    public void When_authenticate_wiht_wrong_credentials_expect_AuthenticationFailedException() throws Exception {
        String userName = "bruenni";

        StormpathAuthenticationService sut = new StormpathAuthenticationServiceBuilder().withApplicationName(applicationName).build();
        try {
            sut.authenticate(userName, "bla blub");
            Assert.fail("no exception");
        }
        catch (AuthenticationFailedException exception)
        {}
    }

}
