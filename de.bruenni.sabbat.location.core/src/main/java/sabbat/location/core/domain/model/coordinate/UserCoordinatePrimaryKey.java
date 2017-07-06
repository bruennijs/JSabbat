package sabbat.location.core.domain.model.coordinate;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by bruenni on 21.09.16.
 */
@PrimaryKeyClass
public class UserCoordinatePrimaryKey implements Serializable {
    @PrimaryKeyColumn(name = "userid", ordinal = 0, type = org.springframework.cassandra.core.PrimaryKeyType.PARTITIONED)
    private String userId;

    @PrimaryKeyColumn(name = "activityid", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private String activityid;

    @PrimaryKeyColumn(name = "captured", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private Date captured;

    /**
     * Constructor
     * @param userId
     * @param activityid
     * @param captured
     */
    public UserCoordinatePrimaryKey(String userId, String activityid, Date captured) {
        this.userId = userId;
        this.activityid = activityid;
        this.captured = captured;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCoordinatePrimaryKey that = (UserCoordinatePrimaryKey) o;

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
        return "UserCoordinatePrimaryKey{" +
                "userId=" + userId +
                ", activityid=" + activityid +
                ", captured=" + captured +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public String getActivityid() {
        return activityid;
    }

    public Date getCaptured() {
        return captured;
    }
}
