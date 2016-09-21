package sabbat.location.core.domain.model;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;
import sabbat.location.core.persistence.activity.ActivityPrimaryKey;

import java.util.Date;

@Table(value = "activity")
public class Activity {

    @PrimaryKey
    private ActivityPrimaryKey key;

    @Column("started")
    private Date started;

    @Column("finished")
    private Date finished;

    @Column("title")
    private String title;

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
}
