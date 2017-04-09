package sabbat.location.core.domain.model;

import com.sun.javafx.binding.StringFormatter;
import domain.events.EventBase;
import infrastructure.common.event.IEvent;
import infrastructure.parser.JsonDtoParser;
import infrastructure.parser.ParserException;
import infrastructure.parser.SerializingException;
import sabbat.location.core.domain.events.ActivityRelationCreatedEvent;
import sabbat.location.core.domain.events.ActivityStartedEvent;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by bruenni on 18.03.17.
 */
@Table(name = "domainevents", schema = "loc")
public class DomainEvent implements Serializable {

	@Id
	@SequenceGenerator(name = "domainevents_seq", sequenceName = "loc.domainevents_id_seq",  initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "domainevents_seq")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER,
		cascade = CascadeType.REFRESH)
	@JoinColumn(name = "activityid1")
	private Long aggregateid;

	@Column(name = "typeid")
	@Convert(converter = LocationDomainEventTypeConverter.class)
	private LocationDomainEventType typeid;

	@Column(name = "document")
	//@Convert(converter = LocationDomainEventConverter.class)
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

	/**
	 * Constructor
	 * @param id
	 * @param aggregateid
	 * @param event
	 * @throws SerializingException
	 */
	public DomainEvent(Long id, Long aggregateid, IEvent<Long, Long> event) throws SerializingException {
		this.id = id;
		this.aggregateid = aggregateid;
		this.document = Serialize(event);
		this.typeid = getTypeId(event);
	}

	private static LocationDomainEventType getTypeId(IEvent<Long, Long> event) {
		return type2Enum.get(event.getClass());
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
		switch (typeid)
		{
			case ActivityStarted:
				return parser.parse(document, ActivityStartedEvent.class);
			case ActivityRelationCreated:
				return parser.parse(document, ActivityRelationCreatedEvent.class);
			default:
				throw new Exception(StringFormatter.format("not mapped domain event type [%1%]", typeid).getValue());
		}
	}

	private String Serialize(IEvent<Long, Long> domainevent) throws SerializingException {
		return parser.serialize(domainevent);
	}
}
