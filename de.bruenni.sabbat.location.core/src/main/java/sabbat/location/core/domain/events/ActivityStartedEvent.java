package sabbat.location.core.domain.events;

import domain.events.EventBase;

import java.util.Date;

/**
 * Created by bruenni on 14.03.17.
 */
public class ActivityStartedEvent extends EventBase<Long, Long> {

	/**
	 * JSON deserialization.
	 */
	public ActivityStartedEvent()
	{
	}

	public ActivityStartedEvent(Date timestamp, Long aggregateId, Long id) {
		super(timestamp, aggregateId, id);
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
