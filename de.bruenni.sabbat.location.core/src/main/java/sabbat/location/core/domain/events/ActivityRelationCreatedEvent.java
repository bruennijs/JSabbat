package sabbat.location.core.domain.events;

import java.util.Date;

/**
 * Created by bruenni on 16.03.17.
 */
public class ActivityRelationCreatedEvent extends ActivityRelationEventBase {

	/**
	 * JSON deserialization.
	 */
	public ActivityRelationCreatedEvent() {}

	/**
	 * Constructor
	 * @param timestamp
	 * @param aggregateId
	 */
	public ActivityRelationCreatedEvent(Long id, Long aggregateId, Long relatedActivity, Date timestamp) {
		super(timestamp, aggregateId, id, relatedActivity);
	}
}
