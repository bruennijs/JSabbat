package identity;

import builder.JwtAuthenticationServiceBuilder;
import builder.JwtTokenAuthenticationBuilder;
import identity.implementation.JwtAuthenticationService;
import infrastructure.identity.AuthenticationFailedException;
import infrastructure.identity.Jwt;
import infrastructure.identity.Token;
import infrastructure.identity.implementation.JJwtTokenAuthentication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.junit.runners.JUnit4;

/**
 * Created by bruenni on 15.10.16.
 */
@RunWith(JUnit4.class)
public class JwtAuthenticationServiceTest {
    @Test
    public void When_create_from_userref_expect_verify_succeeds_with_equal_userref_parsed() throws AuthenticationFailedException {
        String userName = "bruenni";
        JwtAuthenticationService sut = new JwtAuthenticationServiceBuilder().build();
        Token token = sut.authenticate(userName, "");
        UserRef userRef = sut.verify(token);
        Assert.assertEquals(userName, userRef.getName());
    }

    @Test
    public void When_verify_token_expect_no_exception() throws AuthenticationFailedException {
        String userName = "bruenni";
        JwtAuthenticationService sut = new JwtAuthenticationServiceBuilder().build();
        JJwtTokenAuthentication tokenAuthentication = new JwtTokenAuthenticationBuilder().build();
        Token token = sut.authenticate(userName, "");

        tokenAuthentication.verify(token);
    }
}
