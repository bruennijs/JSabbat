package sabbat.location.core.unittest.application.service;

import identity.IAuthenticationService;
import identity.UserRef;
import infrastructure.identity.AuthenticationFailedException;
import infrastructure.parser.SerializingException;
import infrastructure.util.IterableUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import sabbat.location.core.application.service.command.ActivityCreateCommand;
import sabbat.location.core.application.service.command.ActivityUpdateCommand;
import sabbat.location.core.application.service.command.TimeCoordinate;
import sabbat.location.core.application.service.implementation.DefaultActivityApplicationService;
import sabbat.location.core.builder.ActivityApplicationServiceBuilder;
import sabbat.location.core.builder.AuthenticationServiceBuilder;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.coordinate.UserCoordinate;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by bruenni on 01.10.16.
 */
@RunWith(JUnit4.class)
public class ActivityApplicationServiceTest {
    @Test
    public void When_start_expect_returned_activity_contains_id() throws ExecutionException, InterruptedException, AuthenticationFailedException, SerializingException {

        UserRef user = new UserRef("userid", "username", new ArrayList<>());

        DefaultActivityApplicationService sut = new ActivityApplicationServiceBuilder()
                .build();

        ActivityCreateCommand command = new ActivityCreateCommand(user, UUID.randomUUID().toString(), "hello");
        Activity activity = sut.start(command);

        Assert.assertEquals(command.getId(), activity.getUuid());
        Assert.assertEquals(user.getId(), activity.getUserId());
    }

    private IAuthenticationService buildAuthenticationService(UserRef user) throws AuthenticationFailedException {

        return new AuthenticationServiceBuilder()
                    .withUserRef(user)
                    .buildMocked();
    }
}
