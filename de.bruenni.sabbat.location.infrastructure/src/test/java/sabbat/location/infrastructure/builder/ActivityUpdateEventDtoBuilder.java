package sabbat.location.infrastructure.builder;

import sabbat.location.infrastructure.client.dto.ActivityUpdateEventDto;
import sabbat.location.infrastructure.client.dto.TimeSeriesCoordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruenni on 08.10.16.
 */
public class ActivityUpdateEventDtoBuilder {
    private String activityId = "";
    private String token = "";
    private List<TimeSeriesCoordinate> timeSeriesArray = new ArrayList<>();

    public ActivityUpdateEventDto build() {
        return new ActivityUpdateEventDto(this.activityId, this.token, timeSeriesArray);
    }

    public ActivityUpdateEventDtoBuilder withTimeSeries(TimeSeriesCoordinate value) {
        this.timeSeriesArray.add(value);
        return this;
    }
}
