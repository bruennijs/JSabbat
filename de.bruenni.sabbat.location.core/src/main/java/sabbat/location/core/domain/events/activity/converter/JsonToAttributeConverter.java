package sabbat.location.core.domain.events.activity.converter;

import infrastructure.parser.IDtoParser;
import infrastructure.parser.JsonDtoParser;
import infrastructure.parser.ParserException;
import infrastructure.parser.SerializingException;
import sabbat.location.core.domain.events.activity.ActivityRelationCreatedEvent;

import javax.persistence.AttributeConverter;

/**
 * Created by bruenni on 26.05.17.
 */
public class JsonToAttributeConverter<TAttribute> implements AttributeConverter<TAttribute, String>  {

	private static IDtoParser jsonParser = new JsonDtoParser();
	private Class<TAttribute> type;

	public JsonToAttributeConverter(Class<TAttribute> type) {
		this.type = type;
	}

	@Override
	public String convertToDatabaseColumn(TAttribute attribute) {
		try {
			return this.jsonParser.serialize(attribute);
		} catch (SerializingException e) {
			return null;
		}
	}

	@Override
	public TAttribute convertToEntityAttribute(String dbData) {
		try {
			return jsonParser.parse(dbData, type);
		} catch (ParserException e) {
			return null;
		}
	}
}
