package sabbat.location.core.persistence.activity;

import org.springframework.data.cassandra.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by bruenni on 20.09.16.
 */
@PrimaryKeyClass
public class ActivityPrimaryKey implements Serializable {
    //@PrimaryKeyColumn(name = "person_id", ordinal = 0, type = org.springframework.cassandra.core.PrimaryKeyType.PARTITIONED)
    private UUID userId;
    private UUID id;

    /**
     * Constructor
     * @param userId
     * @param id
     */
    public ActivityPrimaryKey(UUID userId, UUID id) {
        this.userId = userId;
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getId() {
        return id;
    }
}
