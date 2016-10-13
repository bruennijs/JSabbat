package sabbat.location.core.domain.model;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * Created by bruenni on 21.09.16.
 */
@Table(ActivityCoordinate.ACTIVITY_COORDINATES_TABLE_NAME)
public class ActivityCoordinate {

    public static final String ACTIVITY_COORDINATES_TABLE_NAME = "activity_coordinates";

    @PrimaryKey
    private ActivityCoordinatePrimaryKey Key;

    @Column("latitude")
    private double latitude;

    @Column("longitude")
    private double longitude;

    public ActivityCoordinate() {
    }

    public ActivityCoordinate(ActivityCoordinatePrimaryKey key, double latitude, double longitude) {
        this.Key = key;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public ActivityCoordinatePrimaryKey getKey() {
        return Key;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivityCoordinate that = (ActivityCoordinate) o;

        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        return Key != null ? Key.equals(that.Key) : that.Key == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = Key != null ? Key.hashCode() : 0;
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "ActivityCoordinate{" +
                "Key=" + Key +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
