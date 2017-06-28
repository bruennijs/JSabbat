package sabbat.location.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by bruenni on 08.10.16.
 */
public class TimeSeriesCoordinate extends TimeSeries {

    @JsonProperty("longitude")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    private double longitude;

    @JsonProperty("latitude")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    private double latitude;

    public TimeSeriesCoordinate() {
    }

    public TimeSeriesCoordinate(Date timestamp, double latitude, double longitude) {
        super(timestamp);
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TimeSeriesCoordinate that = (TimeSeriesCoordinate) o;

        if (Double.compare(that.longitude, longitude) != 0) return false;
        return Double.compare(that.latitude, latitude) == 0;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "TimeSeriesCoordinate{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                "} " + super.toString();
    }
}
