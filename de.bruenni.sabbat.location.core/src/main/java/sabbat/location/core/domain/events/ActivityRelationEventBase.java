package sabbat.location.core.domain.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import domain.events.EventBase;

import java.util.Date;

/**
 * Created by bruenni on 16.03.17.
 */
public abstract class ActivityRelationEventBase extends EventBase<Long, Long> {
	@JsonProperty("relatedActivity")
	protected Long relatedActivity;

	/**
	 * JSON deserialization.
	 */
	public ActivityRelationEventBase() {
	}

	/**
	 *
	 * @param timestamp
	 * @param aggregateId
	 * @param id
	 * @param relatedActivityId
	 */
	public ActivityRelationEventBase(Date timestamp, Long aggregateId, Long id, Long relatedActivityId) {
		super(timestamp, aggregateId, id);
		this.relatedActivity = relatedActivityId;
	}

	public Long getRelatedActivityId() {
		return relatedActivity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ActivityRelationEventBase that = (ActivityRelationEventBase) o;

		return relatedActivity.equals(that.relatedActivity);
	}

	@Override
	public int hashCode() {
		return relatedActivity.hashCode();
	}
}
