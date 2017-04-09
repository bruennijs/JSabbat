package sabbat.location.core.domain.events;

import infrastructure.common.event.implementation.Event;

import java.util.Date;

/**
 * Created by bruenni on 16.03.17.
 */
public class ActivityRelationUpdatedEvent extends ActivityRelationEventBase {

	/**
	 * JSON deserialization.
	 */
	public ActivityRelationUpdatedEvent() {
	}

	/**
	 * Constructor
	 * @param id
	 * @param aggregateId
	 * @param relatedActivity
	 * @param timestamp
	 */
	public ActivityRelationUpdatedEvent(Long id, Long aggregateId, Long relatedActivity, Date timestamp) {
		super(timestamp, aggregateId, id, relatedActivity);
	}
}
