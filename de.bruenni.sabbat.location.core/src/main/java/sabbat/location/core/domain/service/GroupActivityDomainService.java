package sabbat.location.core.domain.service;

import infrastructure.common.event.Event;
import sabbat.location.core.domain.events.activity.ActivityStartedEvent;

import java.util.List;

/**
 * Created by bruenni on 01.06.17.
 */
public interface GroupActivityDomainService {
	List<Event> onActivityStarted(ActivityStartedEvent event);
}
