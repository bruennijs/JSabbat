package sabbat.location.core.domain.model;

import java.util.Arrays;

/**
 * Created by bruenni on 09.04.17.
 */
public enum DomainEventType {

	/**
	 * See ActivtyStartedEvent
	 */
	ActivityStarted (1),

	/**
	 * See ActivityRelationCreatedEvent
	 */
	ActivityRelationCreated(2),

	/**
	 * TBD
	 */
	NewDistanceEvent(3),

	/**
	 * TBD
	 */
	ActivityRelationUpdated(4);

	private final Integer value;

	/**
	 * Constructor
	 * @param value
	 */
	DomainEventType(Integer value)
	{
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	/**
	 * Converts short to corresponding enum type.
	 * @param value
	 * @return
	 */
	public static DomainEventType fromValue(Integer value) {
		return Arrays.stream(values()).filter(cur -> cur.getValue() == value).findFirst().get();
	}
}
