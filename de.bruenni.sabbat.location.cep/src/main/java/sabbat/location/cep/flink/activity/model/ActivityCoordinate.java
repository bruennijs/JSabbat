package sabbat.location.cep.flink.activity.model;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 * Created by bruenni on 15.07.17.
 */
public class ActivityCoordinate {
    private String activityId;
    private TimeSeriesCoordinate coordinate;

    public ActivityCoordinate() {
    }

    /**
     * Constructor
     * @param activityId
     * @param coordinate
     */
    public ActivityCoordinate(String activityId, TimeSeriesCoordinate coordinate) {
        this.activityId = activityId;
        this.coordinate = coordinate;
    }

    public void setCoordinate(TimeSeriesCoordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityId() {
        return activityId;
    }

    public TimeSeriesCoordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Compares only by timestamp.
     * @return
     */
    public static Comparator<ActivityCoordinate> comparatorByTimeStamp()
    {
        return new Comparator<ActivityCoordinate>() {
            @Override
            public int compare(ActivityCoordinate o1, ActivityCoordinate o2) {
                return o1.getCoordinate().getTimestamp().compareTo(o2.getCoordinate().getTimestamp());
            }
        };
    }

    @Override
    public String toString() {
        return "ActivityCoordinate{" +
                "activityId='" + activityId + '\'' +
                ", coordinate=" + coordinate +
                '}';
    }
}
