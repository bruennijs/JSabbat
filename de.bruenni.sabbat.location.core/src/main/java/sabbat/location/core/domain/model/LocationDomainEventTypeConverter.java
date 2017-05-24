package sabbat.location.core.domain.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by bruenni on 09.04.17.
 */
@Converter()
public class LocationDomainEventTypeConverter implements AttributeConverter<DomainEventType, Integer> {
	@Override
	public Integer convertToDatabaseColumn(DomainEventType attribute) {
		return attribute.getValue();
	}

	@Override
	public DomainEventType convertToEntityAttribute(Integer dbData) {
		return DomainEventType.fromValue(dbData);
	}
}
