package sabbat.location.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.geo.Point;

import java.util.Date;
import java.util.Map;
//import de.bruenni.infrastructure.common.util.Tuple2;

/**
 * Created by bruenni on 02.10.16.
 */
public class ActivityUpdateEventDto extends ActivityDtoBase  {

/*    @JsonProperty()
    private Tuple2<Date, Point> tuples;*/

    @JsonProperty("timestamp")
    private Date timestamp;

    @JsonProperty("coordinate")
    private Point coordinate;

    /**
     * Constructor
     * @param activityId
     * @param timestamp
     * @param coordinate
     */
    public ActivityUpdateEventDto(String activityId, Date timestamp, Point coordinate) {
        super(activityId);
        this.timestamp = timestamp;
        this.coordinate = coordinate;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Point getCoordinate() {
        return coordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ActivityUpdateEventDto that = (ActivityUpdateEventDto) o;

        if (!timestamp.equals(that.timestamp)) return false;
        return coordinate.equals(that.coordinate);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + timestamp.hashCode();
        result = 31 * result + coordinate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ActivityUpdateEventDto{" +
                "timestamp=" + timestamp +
                ", coordinate=" + coordinate +
                "} " + super.toString();
    }
}
