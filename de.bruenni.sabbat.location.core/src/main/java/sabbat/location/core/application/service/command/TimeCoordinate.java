package sabbat.location.core.application.service.command;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by bruenni on 06.11.16.
 */
public class TimeCoordinate {

    private double longitude;

    private double latitude;

    private Date timestamp;

    /**
     * Constructor
     * @param longitude
     * @param latitude
     * @param timestamp
     */
    public TimeCoordinate(double longitude, double latitude, Date timestamp) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.timestamp = timestamp;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
