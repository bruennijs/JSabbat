package sabbat.location.core.application.service.implementation;

import identity.IAuthenticationService;
import identity.UserRef;
import infrastructure.common.event.Event;
import infrastructure.common.event.IDomainEventBus;
import infrastructure.identity.AuthenticationFailedException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import sabbat.location.core.application.service.GroupActivityApplicationService;
import sabbat.location.core.domain.events.activity.ActivityStartedEvent;
import sabbat.location.core.domain.service.DefaultGroupActivityDomainService;
import sabbat.location.core.persistence.activity.IActivityRepository;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by bruenni on 14.03.17.
 */
public class DefaultGroupActivityApplicationService implements GroupActivityApplicationService, ApplicationEventPublisherAware {

	private IAuthenticationService authenticationService;
	private DefaultGroupActivityDomainService domainService;
	private IActivityRepository activityRepository;
	private ApplicationEventPublisher applicationEventPublisher;

	/**
	 * Constructor
	 * @param authenticationService
	 * @param domainService
	 */
	public DefaultGroupActivityApplicationService(IAuthenticationService authenticationService, DefaultGroupActivityDomainService domainService) {
		this.authenticationService = authenticationService;
		this.domainService = domainService;
		this.activityRepository = activityRepository;
	}

	@Override
	public void updateGroupActivity(GroupActivityUpdateCommand command) throws AuthenticationFailedException {
		UserRef userRef1 = authenticationService.verify(command.getUserToken1());
		UserRef userRef2 = authenticationService.verify(command.getUserToken2());
	}

	/**
	 * Domain events handling:
	 * 1) @{@link ActivityStartedEvent}
	 */
	@EventListener
	public void onActivityStarted(ActivityStartedEvent activityStarted)
	{
		List<Event> domainEvents = domainService.onNewActivityStarted(activityStarted);
		domainEvents.stream().forEach(e -> applicationEventPublisher.publishEvent(e));
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
}
