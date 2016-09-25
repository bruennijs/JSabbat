package sabbat.location.core.domain.model;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by bruenni on 21.09.16.
 */
@PrimaryKeyClass
public class ActivityCoordinatePrimaryKey implements Serializable {
    @PrimaryKeyColumn(name = "userid", ordinal = 0, type = org.springframework.cassandra.core.PrimaryKeyType.PARTITIONED)
    private UUID userId;

    @PrimaryKeyColumn(name = "activityid", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private UUID activityid;

    @PrimaryKeyColumn(name = "captured", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private Date captured;

    /**
     * Constructor
     * @param userId
     * @param activityid
     * @param captured
     */
    public ActivityCoordinatePrimaryKey(UUID userId, UUID activityid, Date captured) {
        this.userId = userId;
        this.activityid = activityid;
        this.captured = captured;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivityCoordinatePrimaryKey that = (ActivityCoordinatePrimaryKey) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (activityid != null ? !activityid.equals(that.activityid) : that.activityid != null) return false;
        return captured != null ? captured.equals(that.captured) : that.captured == null;

    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (activityid != null ? activityid.hashCode() : 0);
        result = 31 * result + (captured != null ? captured.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ActivityCoordinatePrimaryKey{" +
                "userId=" + userId +
                ", activityid=" + activityid +
                ", captured=" + captured +
                '}';
    }
}
