package sabbat.location.core.domain.model;

import javax.persistence.AttributeConverter;

/**
 * Created by bruenni on 17.03.17.
 */
public class IntegerToLongConverter implements AttributeConverter<Long, Integer> {
	@Override
	public Integer convertToDatabaseColumn(Long attribute) {
		return Integer.valueOf(attribute.intValue());
	}

	@Override
	public Long convertToEntityAttribute(Integer dbData) {
		return Long.valueOf(dbData.longValue());
	}
}
