package sabbat.location.core.domain.events;

import domain.events.EventBase;

import java.util.Date;

/**
 * Created by bruenni on 16.03.17.
 */
public abstract class ActivityRelationEventBase extends EventBase {
	protected Long[] activityIds = new Long[2];

	public ActivityRelationEventBase(Date timestamp, Long aggregateId, Long id, Long[] activityIds) {
		super(timestamp, aggregateId, id);
		this.activityIds = activityIds;
	}

	public Long[] getActivityIds() {
		return activityIds;
	}
}
