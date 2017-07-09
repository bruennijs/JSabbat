package identity;

import builder.JJwtTokenAuthenticationBuilder;
import builder.JwtAuthenticationServiceBuilder;
import identity.implementation.JwtAuthenticationService;
import infrastructure.identity.AuthenticationFailedException;
import infrastructure.identity.Token;
import infrastructure.identity.implementation.JJwtTokenAuthentication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by bruenni on 15.10.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class JwtAuthenticationServiceTest {
    @Test
    public void When_create_from_userref_expect_verify_succeeds_with_equal_userref_parsed() throws AuthenticationFailedException {
        String userName = "test";
        JwtAuthenticationService sut = new JwtAuthenticationServiceBuilder().build();
        Token token = sut.authenticate(userName, "");
        UserRef userRef = sut.verify(token);
        Assert.assertEquals(userName, userRef.getName());
    }

    @Test
    public void When_verify_token_expect_no_exception() throws AuthenticationFailedException {
        String userName = "test";
        JwtAuthenticationService sut = new JwtAuthenticationServiceBuilder().build();
        JJwtTokenAuthentication tokenAuthentication = new JJwtTokenAuthenticationBuilder().build();
        Token token = sut.authenticate(userName, "");

        tokenAuthentication.verify(token);
    }
}
