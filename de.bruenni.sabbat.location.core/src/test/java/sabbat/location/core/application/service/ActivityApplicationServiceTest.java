package sabbat.location.core.application.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.concurrent.ListenableFuture;
import sabbat.location.core.application.service.command.ActivityCreateCommand;
import sabbat.location.core.builder.ActivityApplicationServiceBuilder;
import sabbat.location.core.domain.model.Activity;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Created by bruenni on 01.10.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ActivityApplicationServiceTest {
    @Test
    public void When_start_expect_returned_activity_contains_id() throws ExecutionException, InterruptedException {
        ActivityApplicationService sut = new ActivityApplicationServiceBuilder().build();
        ActivityCreateCommand command = new ActivityCreateCommand(UUID.randomUUID(), UUID.randomUUID(), "hello");
        Activity activity = sut.start(command);

        Assert.assertEquals(command.getId(), activity.getKey().getId());
        Assert.assertEquals(command.getUserId(), activity.getKey().getUserId());
    }
}
