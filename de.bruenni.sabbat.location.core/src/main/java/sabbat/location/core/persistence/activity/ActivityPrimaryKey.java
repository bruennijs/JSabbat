package sabbat.location.core.persistence.activity;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by bruenni on 20.09.16.
 */
@PrimaryKeyClass
public class ActivityPrimaryKey implements Serializable {
    @PrimaryKeyColumn(name = "userid", ordinal = 0, type = org.springframework.cassandra.core.PrimaryKeyType.PARTITIONED)
    private UUID userId;

    @PrimaryKeyColumn(name = "id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivityPrimaryKey that = (ActivityPrimaryKey) o;

        if (!userId.equals(that.userId)) return false;
        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ActivityPrimaryKey{" +
                "userId=" + userId +
                ", id=" + id +
                '}';
    }
}
