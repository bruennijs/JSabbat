package sabbat.location.core.application.service.implementation;

import identity.IAuthenticationService;
import identity.UserRef;
import infrastructure.common.event.Event;
import infrastructure.identity.AuthenticationFailedException;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import sabbat.location.core.application.service.GroupActivityApplicationService;
import sabbat.location.core.domain.events.activity.ActivityRelationCreatedEvent;
import sabbat.location.core.domain.events.activity.ActivityStartedEvent;
import sabbat.location.core.domain.service.GroupActivityDomainService;
import sabbat.location.core.domain.service.implementation.DefaultGroupActivityDomainService;
import sabbat.location.core.persistence.activity.IActivityRepository;

import java.util.List;

/**
 * Created by bruenni on 14.03.17.
 */
public class DefaultGroupActivityApplicationService implements GroupActivityApplicationService, ApplicationEventPublisherAware {

	private static org.slf4j.Logger Log = LoggerFactory.getLogger(DefaultGroupActivityApplicationService.class);

	private GroupActivityDomainService domainService;
	private ApplicationEventPublisher applicationEventPublisher;

	/**
	 * Constructor
	 * @param domainService
	 */
	public DefaultGroupActivityApplicationService(GroupActivityDomainService domainService) {
		this.domainService = domainService;
	}

	@Override
	public void updateGroupActivity(GroupActivityUpdateCommand command) throws AuthenticationFailedException {
	}

	/**
	 * Domain events handling:
	 * 1) @{@link ActivityStartedEvent}
	 */
	@EventListener
	public void onActivityStarted(ActivityStartedEvent activityStarted)
	{
		Log.debug("onActivityStarted [%1s]", activityStarted.toString());

		List<? extends Event> domainEvents = domainService.onActivityStarted(activityStarted);
		domainEvents.stream().forEach(e -> applicationEventPublisher.publishEvent(e));
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
}
