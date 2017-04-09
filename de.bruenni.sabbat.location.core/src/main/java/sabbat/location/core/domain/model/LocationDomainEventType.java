package sabbat.location.core.domain.model;

import java.util.Arrays;

/**
 * Created by bruenni on 09.04.17.
 */
public enum LocationDomainEventType {

	ActivityStarted ((short)1),

	ActivityRelationCreated((short)2);

	private final Short value;

	/**
	 * Constructor
	 * @param value
	 */
	LocationDomainEventType(Short value)
	{
		this.value = value;
	}

	public short getValue() {
		return value;
	}

	/**
	 * Converts short to corresponding enum type.
	 * @param value
	 * @return
	 */
	public static LocationDomainEventType fromValue(Short value) {
		return Arrays.stream(values()).filter(cur -> cur.getValue() == value).findFirst().get();
	}
}
