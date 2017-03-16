package sabbat.location.core.domain.events;

import domain.events.EventBase;
import infrastructure.common.event.IEvent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by bruenni on 14.03.17.
 */
public class ActivityStartedEvent extends EventBase {
	public ActivityStartedEvent(Date timestamp, Long aggregateId, Long id) {
		super(timestamp, aggregateId, id);
	}
}
