package sabbat.location.core.domain.model.coordinate;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.math.BigDecimal;

/**
 * Created by bruenni on 21.09.16.
 */
@Table(UserCoordinate.TABLE_NAME)
public class UserCoordinate {

    public static final String TABLE_NAME = "coordinates_by_user";

    @PrimaryKey
    private UserCoordinatePrimaryKey Key;

    @Column("latitude")
    private BigDecimal latitude;

    @Column("longitude")
    private BigDecimal longitude;

    @Column("deltaS")
    private BigDecimal deltaS;

    public UserCoordinate() {
    }

    /**
     * Constructor
     * @param key
     * @param latitude
     * @param longitude
     */
    public UserCoordinate(UserCoordinatePrimaryKey key, BigDecimal latitude, BigDecimal longitude) {
        this.Key = key;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Constructor
     * @param key
     * @param latitude
     * @param longitude
     */
    public UserCoordinate(UserCoordinatePrimaryKey key, BigDecimal latitude, BigDecimal longitude, BigDecimal deltaS) {
        this.Key = key;
        this.latitude = latitude;
        this.longitude = longitude;
        this.deltaS = deltaS;
    }

    public UserCoordinatePrimaryKey getKey() {
        return Key;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public BigDecimal getDeltaS() {
        return deltaS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCoordinate that = (UserCoordinate) o;

        if (!Key.equals(that.Key)) return false;
        if (!latitude.equals(that.latitude)) return false;
        if (!longitude.equals(that.longitude)) return false;
        return deltaS != null ? deltaS.equals(that.deltaS) : that.deltaS == null;
    }

    @Override
    public int hashCode() {
        int result = Key.hashCode();
        result = 31 * result + latitude.hashCode();
        result = 31 * result + longitude.hashCode();
        result = 31 * result + (deltaS != null ? deltaS.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserCoordinate{" +
                "Key=" + Key +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", deltaS=" + deltaS +
                '}';
    }
}
