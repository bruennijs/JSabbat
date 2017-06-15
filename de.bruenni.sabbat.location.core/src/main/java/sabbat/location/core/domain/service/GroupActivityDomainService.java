package sabbat.location.core.domain.service;

import infrastructure.common.event.Event;
import sabbat.location.core.domain.events.activity.ActivityStartedEvent;

import java.util.List;

/**
 * Created by bruenni on 01.06.17.
 */
public interface GroupActivityDomainService {
	/**
	 * Handles start of an activity and implementations create associations
	 * to different users and their activities of the same group.
	 * @param event event of the activity started.
	 * @return events created during handling if this event.
	 */
	List<Event> onActivityStarted(ActivityStartedEvent event);
}
