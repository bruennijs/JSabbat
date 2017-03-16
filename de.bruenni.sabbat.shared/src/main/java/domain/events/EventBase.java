package domain.events;

import infrastructure.common.event.IEvent;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by bruenni on 16.03.17.
 */
public class EventBase implements IEvent<Long, Long> {
	private Date timestamp;
	private Long aggregateId;
	private Long id;

	public EventBase(Date timestamp, Long aggregateId, Long id) {
		this.timestamp = timestamp;
		this.aggregateId = aggregateId;
		this.id = id;
	}

	@Override
	public Date getTimestamp() {
		return timestamp;
	}

	@Override
	public Long getAggregateId() {
		return aggregateId;
	}

	@Override
	public Long getId() {
		return id;
	}
}
