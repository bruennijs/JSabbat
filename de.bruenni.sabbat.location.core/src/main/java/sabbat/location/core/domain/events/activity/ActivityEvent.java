package sabbat.location.core.domain.events.activity;

import infrastructure.common.event.Event;
import infrastructure.parser.SerializingException;
import org.springframework.context.ApplicationEvent;
import sabbat.location.core.domain.model.Activity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by bruenni on 18.03.17.
 */
@Entity
@Table(name = "domainevents", schema = "loc")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.INTEGER)
public class ActivityEvent extends ApplicationEvent implements Event<Long, Activity>, Serializable {

	@Id
	@SequenceGenerator(name = "domainevents_seq", sequenceName = "loc.domainevents_id_seq",  initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "domainevents_seq")
	private Long id = Long.valueOf(0);

	@ManyToOne(fetch = FetchType.LAZY,
				cascade = CascadeType.REFRESH)
	@JoinColumn(name = "aggregateid")
	private Activity aggregate;

	//@Column(name = "DTYPE", updatable = false, insertable = false)
	//@Enumerated(EnumType.ORDINAL)
	//private DomainEventType eventType;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "created")
	private Date createdOn;

	private static Object sourceDummy = new Object();

	/**
	 * Default constructor used by hibernate
	 */
	public ActivityEvent()
	{
		super(sourceDummy);
	}

	/**
	 * Constructor
	 * @param aggregate
	 * @throws SerializingException
	 */
	public ActivityEvent(Activity aggregate, Date createdOn) {
		super(aggregate);
		this.aggregate = aggregate;
		this.createdOn = createdOn;
	}

	public Long getId() {
		return id;
	}

	@Override
	public Date getCreatedOn() {
		return createdOn;
	}

	@Override
	public Activity getAggregate() {
		return aggregate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ActivityEvent that = (ActivityEvent) o;

		if (!id.equals(that.id)) return false;
		if (!aggregate.getId().equals(that.aggregate.getId())) return false;
		return createdOn.equals(that.createdOn);
	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + aggregate.hashCode();
		result = 31 * result + createdOn.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "ActivityEvent{" +
			"id=" + id +
			", aggregate=" + aggregate +
			", createdOn=" + createdOn +
			'}';
	}

	/**
	 * Created by bruenni on 09.04.17.
	 */
	public static enum DomainEventType {

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
		ActivityRelationUpdated(4),

		/**
		 * See ActivityStopped
		 */
		ActivityStopped(5);

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
			return Arrays.stream(DomainEventType.values()).filter(cur -> cur.getValue() == value).findFirst().get();
		}
	}
}
