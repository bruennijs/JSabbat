package sabbat.location.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import infrastructure.util.Tuple2;
import org.springframework.data.geo.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
//import de.bruenni.infrastructure.common.util.Tuple2;

/**
 * Created by bruenni on 02.10.16.
 */
public class ActivityUpdateEventDto extends ActivityRequestDtoBase   {

    @JsonProperty("timeseries")
    private List<TimeSeriesCoordinate> timeSeries;

    public ActivityUpdateEventDto() {
    }

    /*    @JsonProperty("timestamp")
    private Date timestamp;

    @JsonProperty("coordinates")
    private Point coordinate;*/

    /**
     * Constructor
     * @param activityId
     */
    public ActivityUpdateEventDto(String activityId, String identitytoken, TimeSeriesCoordinate timeSeries) {
        super(activityId, identitytoken);
        this.timeSeries = new ArrayList<>();
        this.timeSeries.add(timeSeries);
    }

    /**
     * Constructor
     * @param id
     * @param identityToken
     */
    public ActivityUpdateEventDto(String id, String identityToken, List<TimeSeriesCoordinate> timeSeriesArray) {
        super(id, identityToken);
        this.timeSeries = timeSeriesArray;
    }

    public Iterable<TimeSeriesCoordinate> getTimeSeries() {
        return timeSeries;
    }

    @Override
    public String toString() {
        return "ActivityUpdateEventDto{" +
                "timeSeries=" + timeSeries +
                "} " + super.toString();
    }
}
