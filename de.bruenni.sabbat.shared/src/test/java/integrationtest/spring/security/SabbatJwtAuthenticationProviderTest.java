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
import org.springframework.boot.test.context.SpringBootTest;
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
@SpringBootTest(classes = IntegrationTestConfig.class)
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
    public void When_authenticate_where_authenticationprovider_setdetail_is_instanceof_user_expect_JWT_token_parsed_contains_username_and_groups() throws Exception {
        Authentication authToken = this.authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken("username", "password"));
        Assert.assertTrue(authToken instanceof UsernamePasswordAuthenticationToken);

        UsernamePasswordAuthenticationToken userNametoken = (UsernamePasswordAuthenticationToken) authToken;

        //// details contain the sabbat JWT token
        Token token = (Token) userNametoken.getDetails();

        UserRef userRef = authService.verify(token);

        Assert.assertEquals("username",    userRef.getId());    // in case of User instances -> username is taken
        Assert.assertThat(userRef.getGroupRefs().stream().map(gref -> gref.getId()).collect(Collectors.toList())
                        , IsCollectionContaining.hasItems("USERS"));
    }

    @Test
    public void When_authenticate_where_authenticationprovider_setdetail_is_instanceof_UserExtended_expect_JWT_token_parsed_contains_Id_and_groups() throws Exception {
        Authentication authToken = this.authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken("testwithUserExtended", "password"));
        Assert.assertTrue(authToken instanceof UsernamePasswordAuthenticationToken);

        UsernamePasswordAuthenticationToken userNametoken = (UsernamePasswordAuthenticationToken) authToken;

        //// details contain the sabbat JWT token
        Token token = (Token) userNametoken.getDetails();

        UserRef userRef = authService.verify(token);

        Assert.assertEquals("id",    userRef.getId());    // in case of User instances -> username is taken
        Assert.assertThat(userRef.getGroupRefs().stream().map(gref -> gref.getId()).collect(Collectors.toList())
                , IsCollectionContaining.hasItems("USERS"));
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
