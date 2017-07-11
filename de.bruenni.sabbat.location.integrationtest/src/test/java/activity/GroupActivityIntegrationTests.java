package activity;

import account.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sabbat.location.core.application.service.ActivityApplicationService;
import sabbat.location.core.application.service.command.ActivityCreateCommand;
import sabbat.location.core.domain.model.Activity;

import java.time.Clock;
import java.time.Instant;
import java.util.Arrays;


/**
 * Created by bruenni on 01.06.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = {"test", "dev"})
@SpringBootTest(classes = GroupActivityIntegrationTestConfiguration.class)
//@SpringBootApplication
public class GroupActivityIntegrationTests {
	@Autowired
	@Qualifier("activityApplicationService")
	public ActivityApplicationService activityApplicationService;

	@Value("${sabbat.location.integrationtest.userid1}")
	private static String userId1 = "00uau41fdzjgnUYSt0h7";

	@Value("${sabbat.location.integrationtest.userid2}")
	private static String userId2 = "00uay1fn1ucXxlvwm0h7";

	@Autowired
	public ApplicationContext ctx;

	@Test
	public void when_start_activity_expect_spring_context_fires_event_to_eventlistener_of_GroupActivityApplicationService() throws Exception {

		Activity activityUser1 = this.activityApplicationService.start(new ActivityCreateCommand(createUser(userId1), String.format("%1s", Instant.now(Clock.systemUTC()).toString()), "my title user 1"));

		Activity activityUser2 = this.activityApplicationService.start(new ActivityCreateCommand(createUser(userId2), String.format("%1s", Instant.now(Clock.systemUTC()).toString()), "my title user 2"));
		//Activity activity2 = this.activityApplicationService.start(new ActivityCreateCommand(Token.valueOf("some token"), "4711", "my title"));

		//verify(domainServiceMock).onActivityStarted(argThat(new LambdaArgumentMatcher<>(startedEvent -> startedEvent.getAggregate().equals(activity))));

		this.activityApplicationService.stop(activityUser1.getUuid());
		this.activityApplicationService.stop(activityUser2.getUuid());
	}

	private User createUser(String id) {
		return new User(id, "name", "oliver.bruentje@gmx.de", Arrays.asList());
	}
}
