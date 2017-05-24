package sabbat.location.core.domain.events;

import domain.events.EventBase;
import infrastructure.parser.JsonDtoParser;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.ActivityEvent;
import sabbat.location.core.domain.model.DomainEventType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * Created by bruenni on 14.03.17.
 */
@Entity
@DiscriminatorValue(value = "ActivityStartedEvent")
public class ActivityStartedEvent extends ActivityEvent {

	public ActivityStartedEvent()
	{
	}

	public ActivityStartedEvent(Date timestamp, Activity aggregate) {
		super(aggregate, timestamp, DomainEventType.ActivityStarted);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
