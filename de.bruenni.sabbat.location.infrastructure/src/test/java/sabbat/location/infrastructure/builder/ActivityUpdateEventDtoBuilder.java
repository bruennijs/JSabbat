package sabbat.location.infrastructure.builder;

import sabbat.location.infrastructure.client.dto.ActivityUpdateEventDto;
import sabbat.location.infrastructure.client.dto.TimeSeriesCoordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
