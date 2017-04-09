package sabbat.location.core.domain.model;

import javax.persistence.AttributeConverter;

/**
 * Created by bruenni on 09.04.17.
 */
public class LocationDomainEventTypeConverter implements AttributeConverter<LocationDomainEventType, Short> {
	@Override
	public Short convertToDatabaseColumn(LocationDomainEventType attribute) {
		return attribute.getValue();
	}

	@Override
	public LocationDomainEventType convertToEntityAttribute(Short dbData) {
		return LocationDomainEventType.fromValue(dbData);
	}
}
