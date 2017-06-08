package sabbat.location.core.domain.model;

import com.google.common.collect.Lists;
import infrastructure.common.Aggregate;
import infrastructure.common.event.Event;
import infrastructure.common.event.IEventHandler;
import infrastructure.parser.SerializingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sabbat.location.core.domain.events.activity.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.lang.reflect.Type;
import java.time.Clock;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "activity", schema = "loc")
public class Activity implements Aggregate<Long, ActivityEvent>, IEventHandler {

	private static Logger Log = LoggerFactory.getLogger(Activity.class);

	@Id
	@Convert(converter = IntegerToLongConverter.class)
	@SequenceGenerator(name="activity_eq", sequenceName="loc.activity_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activity_eq")
	private Long id;

	@Column(name = "uuid")
	private String uuid;

	@Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "started")
    private Date started;

	@Column(name = "finished")
    private Date finished;

	@Column(name = "title")
    private String title;

	@Column(name = "userid")
	private String userId;

    @OneToMany(cascade = {CascadeType.ALL},
			mappedBy = "activity1",
			orphanRemoval = true)
    private List<ActivityRelation> relations1 = Lists.newLinkedList();

	@OneToMany(cascade = {CascadeType.ALL},
				mappedBy = "activity2",
		orphanRemoval = true)
	private List<ActivityRelation> relations2 = Lists.newArrayList();

	@OneToMany(cascade = {CascadeType.ALL},
				mappedBy = "aggregate",
		       orphanRemoval = true)
	private Set<ActivityEvent> domainEvents = new java.util.HashSet<>();

	/**
	 * Constructor
	 */
    public Activity() {
    }

    /**
     * Activity primary key.
     * @param title
     */
    public Activity(Long id, String uuid, String title, Date started, String userId) {
		this.id = id;
		this.uuid = uuid;
		this.title = title;
        this.started = started;
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

    public String getTitle() {
        return title;
    }

    public Date getStarted() {
        return started;
    }

    public Date getFinished() {
        return finished;
    }

	public String getUserId() {
		return userId;
	}

	public String getUuid() {
		return uuid;
	}

	/**
	 * Set finished after activity stopped.
	 * @param finished
	 */
	public void setFinished(Date finished) {
		this.finished = finished;
	}

    /**
     * Relates an activity with the current activity.
     * For each activity to be related a new ActivityRelation will
	 * be created.
     * @param toBeRelated
     * @return
     */
    public List<ActivityEvent> relateActivity(Activity toBeRelated)
    {
		Instant now = Instant.now(Clock.systemUTC());

		ActivityRelation activityRelation = new ActivityRelation(this, toBeRelated);
		relations1.add(activityRelation);
		toBeRelated.relations2.add(activityRelation);

		// create events
		ActivityEvent eventOfThisActivity = new ActivityRelationCreatedEvent(this, Date.from(now), toBeRelated.getId());
		ActivityEvent eventOfToBeRelated = new ActivityRelationCreatedEvent(toBeRelated, Date.from(now), this.getId());

		// add event to this
		this.domainEvents.add(eventOfThisActivity);
		toBeRelated.domainEvents.add(eventOfToBeRelated);

		return Arrays.asList(eventOfThisActivity, eventOfToBeRelated);
	}

	/**
	 * Starts the activity and creates domainevent
	 * @return
	 */
	public ActivityStartedEvent start() throws SerializingException {
		ActivityStartedEvent domainEvent = new ActivityStartedEvent(this.getStarted(), this);
		this.domainEvents.add(domainEvent);
		return domainEvent;
	}


	public ActivityEvent stop() {

		Instant now = Instant.now(Clock.systemUTC());

		ActivityEvent domainEvent = new ActivityStoppedEvent(Date.from(now), this);
		this.domainEvents.add(domainEvent);
		return domainEvent;
	}

	/**
	 * Merges both related activity relation lists.
	 * @return
	 */
    public List<ActivityRelation> getRelations() {
        return Stream.concat(relations1.stream(), relations2.stream()).collect(Collectors.toList());
    }


	/**
	 * Gets all related activities except this instance.
	 * @return
	 */
	public List<Activity> getRelatedActivities() {
    	return getRelations().stream().flatMap(ar -> ar.getRelatedActivities().stream()).filter(activity -> activity != this).collect(Collectors.toList());
	}

	@Override
	public void OnEvent(Event event) {
		if (event instanceof ActivityRelationCreatedEvent)
		{

		}
	}

	@Override
	public Type[] getSupportedEvents() {
		return new Type[] {ActivityRelationCreatedEvent.class, ActivityRelationUpdatedEvent.class};
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Activity activity = (Activity) o;

		return id.equals(activity.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return "Activity{" +
			"id=" + id +
			", uuid='" + uuid + '\'' +
			", started=" + started +
			", finished=" + finished +
			", title='" + title + '\'' +
			", userId='" + userId + '\'' +
			", relations1=" + relations1 +
			", relations2=" + relations2 +
			'}';
	}

	/**
	 * Gets the domain events created by this entity.
	 * @return
	 * @throws Exception
	 */
	public Iterable<ActivityEvent> getEvents() {
		return domainEvents;
	}
}
