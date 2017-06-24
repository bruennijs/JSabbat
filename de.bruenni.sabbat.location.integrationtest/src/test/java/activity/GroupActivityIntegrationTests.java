package activity;

import account.User;
import identity.UserJwtBuilder;
import infrastructure.identity.Jwt;
import infrastructure.identity.Token;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sabbat.location.core.application.service.ActivityApplicationService;
import sabbat.location.core.application.service.command.ActivityCreateCommand;
import sabbat.location.core.domain.model.Activity;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;


/**
 * Created by bruenni on 01.06.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = {"dev"})
@SpringApplicationConfiguration(classes = GroupActivityIntegrationTestConfiguration.class)
public class GroupActivityIntegrationTests {
	@Autowired
	@Qualifier("activityApplicationService")
	public ActivityApplicationService activityApplicationService;

	@Value("${sabbat.location.integrationtest.userid}")
	private static String userId = "00uau41fdzjgnUYSt0h7";

	@Autowired
	public ApplicationContext ctx;

	@Test
	public void when_start_activity_expect_spring_context_fires_event_to_eventlistener_of_GroupActivityApplicationService() throws Exception {

		Activity activity = this.activityApplicationService.start(new ActivityCreateCommand(createUser(), "4711", "my title"));
		//Activity activity2 = this.activityApplicationService.start(new ActivityCreateCommand(Token.valueOf("some token"), "4711", "my title"));

		//verify(domainServiceMock).onActivityStarted(argThat(new LambdaArgumentMatcher<>(startedEvent -> startedEvent.getAggregate().equals(activity))));
	}

	private User createUser() {
		return new User(userId, "name", "oliver.bruentje@gmx.de", Arrays.asList());
	}
}
