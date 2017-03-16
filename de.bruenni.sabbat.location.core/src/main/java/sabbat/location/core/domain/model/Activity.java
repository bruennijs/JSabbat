package sabbat.location.core.domain.model;

import infrastructure.common.event.IEvent;
import infrastructure.common.event.IEventHandler;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;
import sabbat.location.core.domain.events.ActivityRelationCreatedEvent;
import sabbat.location.core.domain.events.ActivityRelationUpdatedEvent;
import sabbat.location.core.domain.model.relation.ActivityRelation;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(value = "activity")
public class Activity implements IEventHandler {

	@Column("notused")
	private Long id;

    @PrimaryKey
    private ActivityPrimaryKey key;

    @Column("started")
    private Date started;

    @Column("finished")
    private Date finished;

    @Column("title")
    private String title;

    //private List<ActivityRelation> relations;

    public Activity() {
    }

    /**
     * Activity primary key.
     * @param key
     * @param title
     */
    public Activity(ActivityPrimaryKey key, String title, Date started) {
        this.key = key;
        this.title = title;
        this.started = started;
        //this.relations = new ArrayList<ActivityRelation>();
    }

    public ActivityPrimaryKey getKey() {
        return key;
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

    /**
     * Relates an activity with the current activity.
     * For each activity to be related a new ActivityRelation will
	 * be created.
     * @param toBeRelated
     * @return
     */
    public IEvent[] relateActivity(Activity toBeRelated)
    {
		ActivityRelation activityRelation = new ActivityRelation(0, getId(), toBeRelated.getId());
		//relations.add(activityRelation);
		return new IEvent[] {};
    }

    /**
     * Set finished after activity stopped.
     * @param finished
     */
    public void setFinished(Date finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "key=" + key +
                ", started=" + started +
                ", finished=" + finished +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;

        if (!key.equals(activity.key)) return false;
        if (!started.equals(activity.started)) return false;
        if (finished != null ? !finished.equals(activity.finished) : activity.finished != null) return false;
        return title.equals(activity.title);

    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + started.hashCode();
        result = 31 * result + (finished != null ? finished.hashCode() : 0);
        result = 31 * result + title.hashCode();
        return result;
    }

/*    public List<ActivityRelation> getRelations() {
        return relations;
    }*/

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

	public Long getId() {
		return id;
	}
}
