package domain.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import infrastructure.common.event.IEvent;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by bruenni on 16.03.17.
 */
public abstract class EventBase<TId extends Serializable, TAggregateId extends Serializable> implements IEvent<TId, TAggregateId> {
	@JsonProperty("timestamp")
	private Date timestamp;

	@JsonProperty("aggregateid")
	private TAggregateId aggregateId;

	@JsonProperty("id")
	private TId id;

	/**
	 * Needed for json deserialization.
	 */
	public EventBase() {
	}

	/**
	 * Constructor
	 * @param timestamp
	 * @param aggregateId
	 * @param id
	 */
	public EventBase(Date timestamp, TAggregateId aggregateId, TId id) {
		this.timestamp = timestamp;
		this.aggregateId = aggregateId;
		this.id = id;
	}

	@Override
	public Date getTimestamp() {
		return timestamp;
	}

	@Override
	public TAggregateId getAggregateId() {
		return aggregateId;
	}

	@Override
	public TId getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		EventBase<?, ?> eventBase = (EventBase<?, ?>) o;

		if (!timestamp.equals(eventBase.timestamp)) return false;
		if (!aggregateId.equals(eventBase.aggregateId)) return false;
		return id.equals(eventBase.id);
	}

	@Override
	public int hashCode() {
		int result = timestamp.hashCode();
		result = 31 * result + aggregateId.hashCode();
		result = 31 * result + id.hashCode();
		return result;
	}
}
