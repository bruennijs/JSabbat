package sabbat.location.core.domain.model;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.math.BigDecimal;
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
}
