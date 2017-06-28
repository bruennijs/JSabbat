package builder;

import sabbat.location.api.dto.ActivityUpdateEventDto;
import sabbat.location.api.dto.TimeSeriesCoordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruenni on 08.10.16.
 */
public class ActivityUpdateEventDtoBuilder extends ActivityRequestDtoBuilderBase<ActivityUpdateEventDtoBuilder> {
    private List<TimeSeriesCoordinate> timeSeriesArray = new ArrayList<>();

    public ActivityUpdateEventDto build() {
        return new ActivityUpdateEventDto(this.activityId, this.identityToken, timeSeriesArray);
    }

    public ActivityUpdateEventDtoBuilder withTimeSeries(TimeSeriesCoordinate value) {
        this.timeSeriesArray.add(value);
        return this;
    }
}
