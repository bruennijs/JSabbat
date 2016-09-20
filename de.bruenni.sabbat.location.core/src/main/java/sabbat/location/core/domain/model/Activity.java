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
}
