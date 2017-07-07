package sabbat.location.core.unittest.application.service;

import identity.UserRef;
import infrastructure.util.IterableUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import sabbat.location.core.application.service.command.ActivityUpdateCommand;
import sabbat.location.core.application.service.command.TimeCoordinate;
import sabbat.location.core.application.service.implementation.DefaultActivityApplicationService;
import sabbat.location.core.builder.ActivityApplicationServiceBuilder;
import sabbat.location.core.domain.model.coordinate.UserCoordinate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by bruenni on 07.07.17.
 */
@RunWith(JUnit4.class)
public class MeasurementApplicationServiceTest {


    @Test
    public void When_update_expect_ActivityCoordinates_userid_equals_userref_userid() throws Exception {
/*        UserRef user = new UserRef("userid", "username", new ArrayList<>());

        TimeCoordinate timeCoordinate = new TimeCoordinate(34.2, 22.889, new Date());

        ActivityUpdateCommand command = new ActivityUpdateCommand(user, UUID.randomUUID().toString(), Arrays.asList(timeCoordinate), null, null);

        DefaultActivityApplicationService sut = new ActivityApplicationServiceBuilder()
                .build();

        List<UserCoordinate> coordinateEntities = IterableUtils.stream(sut.insert(command)).collect(Collectors.toList());

        Assert.assertEquals(1, coordinateEntities.size());

        UserCoordinate userCoordinate = coordinateEntities.stream().findFirst().get();

        Assert.assertEquals(command.getActivityId(), userCoordinate.getKey().getActivityid());
        Assert.assertEquals(user.getId(), userCoordinate.getKey().getUserId())*/;
    }
}
