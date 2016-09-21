package sabbat.location.infrastructure.integrationtest.persistence.activity;

import sabbat.location.core.domain.model.ActivityCoordinate;
import sabbat.location.core.domain.model.ActivityCoordinatePrimaryKey;
import sabbat.location.core.domain.model.Coordinate;

/**
 * Created by bruenni on 21.09.16.
 */
public class ActivityCoordinateBuilder {
    private sabbat.location.core.domain.model.ActivityCoordinatePrimaryKey pkey;
    private Coordinate coordinate = new CoordinateBuilder().build();

    public ActivityCoordinate build() {
        return new ActivityCoordinate(this.pkey,  coordinate.getLatitude(), coordinate.getLongitude());
    }

    public ActivityCoordinateBuilder withPrimaryKey(ActivityCoordinatePrimaryKey value) {
        pkey = value;
        return this;
    }
}
