package sabbat.location.core.domain.events;

import java.util.Date;

/**
 * Created by bruenni on 16.03.17.
 */
public class ActivityRelationCreatedEvent extends ActivityRelationEventBase {

	/**
	 * Constructor
	 * @param timestamp
	 * @param aggregateId
	 */
	public ActivityRelationCreatedEvent(Date timestamp, Long aggregateId, Long id, Long[] activityIds) {
		super(timestamp, aggregateId, id, activityIds);
	}

}
