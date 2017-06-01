package sabbat.location.core.integrationtest.usecases;

import infrastructure.identity.Token;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.mockito.internal.matchers.Equals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sabbat.location.core.LambdaArgumentMatcher;
import sabbat.location.core.application.service.ActivityApplicationService;
import sabbat.location.core.application.service.command.ActivityCreateCommand;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.service.GroupActivityDomainService;

import java.util.Arrays;

import static org.mockito.Mockito.*;

/**
 * Created by bruenni on 01.06.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ActiveProfiles(profiles = {"test"})
@SpringApplicationConfiguration(classes = UseCasesTestConfig.class)
public class ActivityStartUseCaseTests {
	@Autowired
	@Qualifier(value = "activityApplicationService")
	public ActivityApplicationService activityApplicationService;

	@Autowired
	public ApplicationContext ctx;

	@Test
	public void when_start_activity_expect_spring_context_fires_event_to_eventlistener_of_GroupActivityApplicationService() throws Exception {

		//GroupActivityDomainService domainServiceMock = mock(GroupActivityDomainService.class);
		//when(domainServiceMock.onActivityStarted(Matchers.any())).thenReturn(Arrays.asList());

		//ActivityApplicationService activityApplicationService = (ActivityApplicationService) ctx.getBean("activityApplicationService", domainServiceMock);

		Activity activity = this.activityApplicationService.start(new ActivityCreateCommand(Token.valueOf("some token"), "4711", "my title"));

		//verify(domainServiceMock).onActivityStarted(argThat(new LambdaArgumentMatcher<>(startedEvent -> startedEvent.getAggregate().equals(activity))));
	}
}
