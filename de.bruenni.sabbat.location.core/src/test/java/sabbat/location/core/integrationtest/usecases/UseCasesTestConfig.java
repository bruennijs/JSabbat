package sabbat.location.core.integrationtest.usecases;

import account.IAccountService;
import account.User;
import identity.GroupRef;
import identity.IAuthenticationService;
import identity.UserRef;
import infrastructure.identity.AuthenticationFailedException;
import notification.NotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rx.Observable;
import sabbat.location.core.application.service.ActivityApplicationService;
import sabbat.location.core.application.service.GroupActivityApplicationService;
import sabbat.location.core.application.service.implementation.DefaultActivityApplicationService;
import sabbat.location.core.application.service.implementation.DefaultGroupActivityApplicationService;
import sabbat.location.core.builder.AuthenticationServiceBuilder;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.service.GroupActivityDomainService;
import sabbat.location.core.domain.service.implementation.DefaultGroupActivityDomainService;
import sabbat.location.core.domain.service.implementation.NotificationDomainService;
import sabbat.location.core.persistence.activity.IActivityRepository;

import java.util.Arrays;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by bruenni on 01.06.17.
 */
@Configuration
public class UseCasesTestConfig {

	@Bean
	public IAuthenticationService authenticationService() throws AuthenticationFailedException {
		return new AuthenticationServiceBuilder().withUserRef(new UserRef("userId", "userName", Arrays.asList())).buildMocked();
	}

	@Bean(name = "activityRepository")
	public IActivityRepository activityRepository()
	{
		IActivityRepository mock = mock(IActivityRepository.class);

		doAnswer(invocation -> invocation.getArgument(0))
			.when(mock)
			.save(org.mockito.Matchers.any(Activity.class));

		return mock;
	}

	@Bean
	public IAccountService accountService()
	{
		IAccountService mock = mock(IAccountService.class);
		when(mock.getUsersByGroup(org.mockito.Matchers.any(GroupRef.class))).thenReturn(Arrays.asList(new User("", "")));
		//when(mock.getUserById(org.mockito.Matcherss.)).thenReturn();
		return mock;
	}

	@Bean(name = "activityApplicationService")
	public ActivityApplicationService activityApplicationService(IAuthenticationService authenticationService, IActivityRepository activityRepository)
	{
		return new DefaultActivityApplicationService(activityRepository, authenticationService);
	}

	@Bean
	public GroupActivityDomainService groupActivityDomainService(IActivityRepository activityRepository, IAccountService accountService)
	{
		return new DefaultGroupActivityDomainService(activityRepository, accountService);
	}

	@Bean
	public GroupActivityApplicationService groupActivityApplicationService(IAuthenticationService authenticationService, GroupActivityDomainService groupActivityDomainService)
	{
		return new DefaultGroupActivityApplicationService(authenticationService, groupActivityDomainService);
	}

	@Bean
	public NotificationDomainService notificationDomainService(NotificationService notificationService)
	{
		return new NotificationDomainService(accountService(), activityRepository(), notificationService);
	}

	@Bean
	public NotificationService notificationService()
	{
		NotificationService mock = mock(NotificationService.class);
		when(mock.notify(org.mockito.Matchers.any(User.class), org.mockito.Matchers.any(String.class))).thenReturn(Observable.empty());
		return mock;
	}
}
