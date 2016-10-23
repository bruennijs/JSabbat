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

    /**
     * Constructor
     * @param id
     * @param identityToken
     */
    public ActivityUpdateEventDto(String id, String identityToken, List<TimeSeriesCoordinate> timeSeriesArray) {
        super(id, identityToken);
        this.timeSeries = timeSeriesArray;
    }

    public List<TimeSeriesCoordinate> getTimeSeries() {
        return timeSeries;
    }

    @Override
    public String toString() {
        return "ActivityUpdateEventDto{" +
                "timeSeries=" + timeSeries +
                "} " + super.toString();
    }
}
