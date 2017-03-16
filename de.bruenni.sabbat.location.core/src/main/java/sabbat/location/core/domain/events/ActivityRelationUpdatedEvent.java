package sabbat.location.core.domain.events;

import infrastructure.common.event.implementation.Event;

import java.util.Date;

/**
 * Created by bruenni on 16.03.17.
 */
public class ActivityRelationUpdatedEvent extends ActivityRelationEventBase {
	public ActivityRelationUpdatedEvent(Date timestamp, Long aggregateId, Long id, Long[] activityIds) {
		super(timestamp, aggregateId, id, activityIds);
	}
}
