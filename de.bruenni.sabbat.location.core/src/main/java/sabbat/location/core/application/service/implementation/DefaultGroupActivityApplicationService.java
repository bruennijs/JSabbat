package sabbat.location.core.application.service.implementation;

import identity.IAuthenticationService;
import identity.UserRef;
import infrastructure.common.event.IDomainEventBus;
import infrastructure.common.event.IEvent;
import infrastructure.identity.AuthenticationFailedException;
import sabbat.location.core.application.service.GroupActivityApplicationService;
import sabbat.location.core.domain.events.ActivityStartedEvent;
import sabbat.location.core.domain.service.DefaultGroupActivityDomainService;
import sabbat.location.core.persistence.activity.IActivityRepository;

import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * Created by bruenni on 14.03.17.
 */
public class DefaultGroupActivityApplicationService implements GroupActivityApplicationService {

	private IAuthenticationService authenticationService;
	private DefaultGroupActivityDomainService domainService;
	private IActivityRepository activityRepository;
	private IDomainEventBus domainEventBus;

	public DefaultGroupActivityApplicationService(IAuthenticationService authenticationService, DefaultGroupActivityDomainService domainService, IDomainEventBus domainEventBus) {
		this.authenticationService = authenticationService;
		this.domainService = domainService;
		this.activityRepository = activityRepository;
		this.domainEventBus = domainEventBus;
	}

	@Override
	public void updateGroupActivity(GroupActivityUpdateCommand command) throws AuthenticationFailedException {
		UserRef userRef1 = authenticationService.verify(command.getUserToken1());
		UserRef userRef2 = authenticationService.verify(command.getUserToken2());
	}

	/**
	 * Domain events handling:
	 * 1) @{@link sabbat.location.core.domain.events.ActivityStartedEvent}
	 * @param iEvent
	 */
	@Override
	public void OnEvent(IEvent iEvent) {
		if (iEvent.getClass().equals(ActivityStartedEvent.class))
		{
			IEvent[] domainEvents = domainService.onNewActivityStarted((ActivityStartedEvent) iEvent);
			Arrays.stream(domainEvents).forEach(e -> domainEventBus.publish(e));
		}
	}

	@Override
	public Type[] getSupportedEvents() {
		return new Type[] {ActivityStartedEvent.class};
	}
}
