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
    private float latitude;

    @Column("longitude")
    private float longitude;

    public ActivityCoordinate() {
    }

    public ActivityCoordinate(ActivityCoordinatePrimaryKey key, float latitude, float longitude) {
        Key = key;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public ActivityCoordinatePrimaryKey getKey() {
        return Key;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivityCoordinate that = (ActivityCoordinate) o;

        if (Float.compare(that.latitude, latitude) != 0) return false;
        if (Float.compare(that.longitude, longitude) != 0) return false;
        return Key != null ? Key.equals(that.Key) : that.Key == null;

    }

    @Override
    public int hashCode() {
        int result = Key != null ? Key.hashCode() : 0;
        result = 31 * result + (latitude != +0.0f ? Float.floatToIntBits(latitude) : 0);
        result = 31 * result + (longitude != +0.0f ? Float.floatToIntBits(longitude) : 0);
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
