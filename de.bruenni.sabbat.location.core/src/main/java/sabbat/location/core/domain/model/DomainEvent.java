package sabbat.location.core.domain.model;

import com.sun.javafx.binding.StringFormatter;
import infrastructure.common.event.IEvent;
import infrastructure.parser.JsonDtoParser;
import infrastructure.parser.SerializingException;
import sabbat.location.core.domain.events.ActivityRelationCreatedEvent;
import sabbat.location.core.domain.events.ActivityStartedEvent;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by bruenni on 18.03.17.
 */
@Entity
@Table(name = "domainevents", schema = "loc")
public class DomainEvent implements Serializable {

	private static LocationDomainEventTypeConverter converter = new LocationDomainEventTypeConverter();

	@Id
	@SequenceGenerator(name = "domainevents_seq", sequenceName = "loc.domainevents_id_seq",  initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "domainevents_seq")
	private Long id;

	@JoinColumn(name = "aggregateid")
	@Column(name = "aggregateid")
	private Long aggregateId;

	@Column(name = "typeid")
	//@Convert(converter = LocationDomainEventTypeConverter.class)
	private Short typeidShort;

	@Column(name = "document")
	private String document;

	private static JsonDtoParser parser = new JsonDtoParser();

	private static HashMap<Class, LocationDomainEventType> type2Enum = new HashMap<>();

	/**
	 * Init hashmap
	 */
	static
	{
		type2Enum.put(ActivityStartedEvent.class, LocationDomainEventType.ActivityStarted);
		type2Enum.put(ActivityRelationCreatedEvent.class, LocationDomainEventType.ActivityRelationCreated);
	}

	private String Serialize(IEvent<Long, Long> domainevent) throws SerializingException {
		return parser.serialize(domainevent);
	}

	private static LocationDomainEventType getTypeId(IEvent<Long, Long> event) {
		return type2Enum.get(event.getClass());
	}

	/**
	 * Default constructor used by hibernate
	 */
	public DomainEvent() {
	}

	/**
	 * Constructor
	 * @param id
	 * @param aggregateid
	 * @param event
	 * @throws SerializingException
	 */
	public DomainEvent(Long id, Long aggregateid, IEvent<Long, Long> event) throws SerializingException {
		this.id = id;
		this.aggregateId = aggregateid;
		this.document = Serialize(event);
		setEventType(getTypeId(event));
	}

	public Long getId() {
		return id;
	}

	/**
	 * Parses domain event from document string.
	 * @return
	 * @throws Exception
	 */
	public IEvent<Long, Long> getDomainEvent() throws Exception {
		switch (getEventType())
		{
			case ActivityStarted:
				return parser.parse(document, ActivityStartedEvent.class);
			case ActivityRelationCreated:
				return parser.parse(document, ActivityRelationCreatedEvent.class);
			default:
				throw new Exception(StringFormatter.format("not mapped domain event type [%1%]", getEventType()).getValue());
		}
	}

	public LocationDomainEventType getEventType()
	{
		return converter.convertToEntityAttribute(typeidShort);
	}

	public void setEventType(LocationDomainEventType eventType)
	{
		typeidShort = converter.convertToDatabaseColumn(eventType);
	}
}
