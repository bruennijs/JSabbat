package sabbat.location.core.domain.model;

import com.google.common.collect.Lists;
import infrastructure.common.event.IEvent;
import infrastructure.common.event.IEventHandler;
import sabbat.location.core.domain.events.ActivityRelationCreatedEvent;
import sabbat.location.core.domain.events.ActivityRelationUpdatedEvent;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "activity", schema = "loc")
public class Activity implements IEventHandler, Serializable {

	@Id
	@Convert(converter = IntegerToLongConverter.class)
	@SequenceGenerator(name="activity_eq", sequenceName="loc.activity_id_seq")
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "activity1", orphanRemoval = true)
    private List<ActivityRelation> relations1 = Lists.newArrayList();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "activity2", orphanRemoval = true)
	private List<ActivityRelation> relations2 = Lists.newArrayList();

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
    public IEvent[] relateActivity(Activity toBeRelated)
    {
		ActivityRelation activityRelation = new ActivityRelation(0l, this, toBeRelated);
		//relations.add(activityRelation);
		return new IEvent[] {};
    }

	/**
	 * Merges both related activity relation lists.
	 * @return
	 */
    public List<ActivityRelation> getRelations() {
        return Stream.concat(relations1.stream(), relations2.stream()).collect(Collectors.toList());
    }

	@Override
	public void OnEvent(IEvent iEvent) {
		if (iEvent instanceof ActivityRelationCreatedEvent)
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
			", started=" + started +
			", finished=" + finished +
			", title='" + title + '\'' +
			", relations1=" + relations1 +
			", relations2=" + relations2 +
			'}';
	}
}
