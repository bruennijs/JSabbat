package sabbat.location.core.domain.events.activity;

import sabbat.location.core.domain.model.Activity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * Created by bruenni on 14.03.17.
 */
@Entity
@DiscriminatorValue(value = "5")
public class ActivityStoppedEvent extends ActivityEvent {

	public ActivityStoppedEvent()
	{
	}

	public ActivityStoppedEvent(Date timestamp, Activity aggregate) {
		super(aggregate, timestamp);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "ActivityStoppedEvent{} " + super.toString();
	}
}
