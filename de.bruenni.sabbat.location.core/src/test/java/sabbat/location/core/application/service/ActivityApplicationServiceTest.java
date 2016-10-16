package sabbat.location.core.application.service;

import identity.IAuthenticationService;
import identity.UserRef;
import infrastructure.identity.AuthenticationFailedException;
import infrastructure.identity.Token;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import sabbat.location.core.application.service.command.ActivityCreateCommand;
import sabbat.location.core.builder.ActivityApplicationServiceBuilder;
import sabbat.location.core.builder.AuthenticationServiceBuilder;
import sabbat.location.core.domain.model.Activity;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Created by bruenni on 01.10.16.
 */
@RunWith(JUnit4.class)
public class ActivityApplicationServiceTest {
    @Test
    public void When_start_expect_returned_activity_contains_id() throws ExecutionException, InterruptedException, AuthenticationFailedException {
        UserRef user = new UserRef("", "username", new ArrayList<>());
        IAuthenticationService authenticationService = new AuthenticationServiceBuilder()
                .withUserRef(user)
                .buildMocked();

        ActivityApplicationService sut = new ActivityApplicationServiceBuilder()
                .withAuthenticationService(authenticationService)
                .build();

        ActivityCreateCommand command = new ActivityCreateCommand(Token.valueOf("something"), UUID.randomUUID().toString(), "hello");
        Activity activity = sut.start(command);

        Assert.assertEquals(command.getId(), activity.getKey().getId());
        Assert.assertEquals(user.getName(), activity.getKey().getUserId());
    }
}
