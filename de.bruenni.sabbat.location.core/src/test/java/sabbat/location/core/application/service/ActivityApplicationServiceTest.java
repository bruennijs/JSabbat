package sabbat.location.core.application.service;

import identity.IAuthenticationService;
import identity.UserRef;
import infrastructure.identity.AuthenticationFailedException;
import infrastructure.identity.Token;
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
import sabbat.location.core.domain.model.ActivityCoordinate;

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

        IAuthenticationService authenticationService = buildAuthenticationService(user);

        DefaultActivityApplicationService sut = new ActivityApplicationServiceBuilder()
                .withAuthenticationService(authenticationService)
                .build();

        ActivityCreateCommand command = new ActivityCreateCommand(Token.valueOf("something"), UUID.randomUUID().toString(), "hello");
        Activity activity = sut.start(command);

        Assert.assertEquals(command.getId(), activity.getUuid());
        Assert.assertEquals(user.getId(), activity.getUserId());
    }

    private IAuthenticationService buildAuthenticationService(UserRef user) throws AuthenticationFailedException {

        return new AuthenticationServiceBuilder()
                    .withUserRef(user)
                    .buildMocked();
    }

    @Test
    public void When_update_expect_ActivityCoordinates_userid_equals_userref_userid() throws Exception {
        UserRef user = new UserRef("userid", "username", new ArrayList<>());

        IAuthenticationService authenticationService = buildAuthenticationService(user);

        TimeCoordinate timeCoordinate = new TimeCoordinate(34.2, 22.889, new Date());

        ActivityUpdateCommand command = new ActivityUpdateCommand(Token.valueOf("something"), UUID.randomUUID().toString(), Arrays.asList(timeCoordinate), null, null);

        DefaultActivityApplicationService sut = new ActivityApplicationServiceBuilder()
                .withAuthenticationService(authenticationService)
                .build();

        List<ActivityCoordinate> coordinateEntities = IterableUtils.stream(sut.update(command)).collect(Collectors.toList());

        Assert.assertEquals(1, coordinateEntities.size());

        ActivityCoordinate activityCoordinate = coordinateEntities.stream().findFirst().get();

        Assert.assertEquals(command.getActivityId(), activityCoordinate.getKey().getActivityid());
        Assert.assertEquals(user.getId(), activityCoordinate.getKey().getUserId());
    }
}
