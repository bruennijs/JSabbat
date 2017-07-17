package sabbat.location.cep.flink.activity.model;

import infrastructure.tracking.GeoPoint;
import infrastructure.tracking.GeoPointBD;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Created by bruenni on 08.10.16.
 */
public class TimeSeriesCoordinate extends TimeSeries {

    private GeoPointBD coordinate;

    public TimeSeriesCoordinate() {
    }

    public TimeSeriesCoordinate(Instant timestamp, GeoPointBD coordinate) {
        super(timestamp);
        this.coordinate = coordinate;
    }

    public void setCoordinate(GeoPointBD coordinate) {
        this.coordinate = coordinate;
    }

    public GeoPointBD getCoordinate() {
        return coordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TimeSeriesCoordinate that = (TimeSeriesCoordinate) o;

        return coordinate.equals(that.coordinate);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + coordinate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TimeSeriesCoordinate{" +
                "coordinate=" + coordinate +
                '}';
    }
}
