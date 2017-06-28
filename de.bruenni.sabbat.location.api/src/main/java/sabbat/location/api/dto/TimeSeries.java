package sabbat.location.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by bruenni on 08.10.16.
 */
public abstract class TimeSeries {
    @JsonProperty("timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date timestamp;

    public TimeSeries() {
    }

    public TimeSeries(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeSeries that = (TimeSeries) o;

        return timestamp != null ? timestamp.equals(that.timestamp) : that.timestamp == null;

    }

    @Override
    public int hashCode() {
        return timestamp != null ? timestamp.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TimeSeries{" +
                "timestamp=" + timestamp +
                '}';
    }
}
