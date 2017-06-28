package builder;


import sabbat.location.api.dto.TimeSeriesCoordinate;

import java.time.Instant;
import java.util.Date;

/**
 * Created by bruenni on 09.10.16.
 */
public class TimeSeriesCoordinateBuilder {
    private double latitude = 1.0;
    private Date date = Date.from(Instant.now());
    private double longitude = 2.0;

    public TimeSeriesCoordinateBuilder withLatitude(double value) {
        this.latitude = value;
        return this;
    }

    public TimeSeriesCoordinate build() {
        return new TimeSeriesCoordinate(this.date, this.latitude, this.longitude);
    }
}
