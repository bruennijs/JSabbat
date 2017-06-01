package integrationtest.spring.security;

import identity.IAuthenticationService;
import identity.UserRef;
import infrastructure.identity.Token;
import integrationtest.identity.IntegrationTestConfig;
import org.hamcrest.core.IsCollectionContaining;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.stream.Collectors;

/**
 * Created by bruenni on 15.10.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test-stormpath")
@SpringApplicationConfiguration(classes = IntegrationTestConfig.class)
public class SabbatJwtAuthenticationProviderTest {
    @Value("${stormpath.application.name}")
    public String applicationName;

    @Autowired
    @Qualifier("authenticationProvider")
    public AuthenticationProvider authenticationProvider;

    @Autowired
    @Qualifier("verifyingAuthenticationService")
    public IAuthenticationService authService;

    @Test
    public void When_authenticate_expect_JWT_token_parsed_contains_username_and_groups() throws Exception {
        String userName = "test";
        Authentication authToken = this.authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(userName, "password"));
        Assert.assertTrue(authToken instanceof UsernamePasswordAuthenticationToken);

        UsernamePasswordAuthenticationToken userNametoken = (UsernamePasswordAuthenticationToken) authToken;

        //// details contain the sabbat JWT token
        Token token = (Token) userNametoken.getDetails();

        UserRef userRef = authService.verify(token);

        Assert.assertEquals("https://api.stormpath.com/v1/accounts/2630FgFzIutkN2IK8H4jk2", userRef.getName());
        Assert.assertThat(userRef.getGroups().stream().map(gref -> gref.getId()).collect(Collectors.toList())
                        , IsCollectionContaining.hasItems("https://api.stormpath.com/v1/groups/2xHJtcQf2PxAqAZuFmEiao",
                                                            "https://api.stormpath.com/v1/groups/6vTQw8pRzoRoFHZbtvOPUj"));
    }

    @Test
    public void When_authenticate_with_wrong_crednetials_expect_AuthenticationException()  {
        String userName = "test";
        try
        {
            this.authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(userName, "wrongpwd"));
            Assert.fail("Must throw AuthenticationException");
        }
        catch (AuthenticationException aException)
        {
        }
    }
}
