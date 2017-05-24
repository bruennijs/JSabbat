package sabbat.location.core.domain.events;

import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.ActivityEvent;
import sabbat.location.core.domain.model.DomainEventType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * Created by bruenni on 16.03.17.
 */
@Entity
@DiscriminatorValue(value = "U")
public class ActivityRelationUpdatedEvent extends ActivityEvent {

	/**
	 * JSON deserialization.
	 */
	public ActivityRelationUpdatedEvent() {
	}

	/**
	 * Constructor
	 * @param relatedActivity
	 * @param timestamp
	 */
	public ActivityRelationUpdatedEvent(Activity aggregate, Long relatedActivity, Date timestamp) {
		super(aggregate, timestamp, DomainEventType.ActivityRelationUpdated);
	}
}
