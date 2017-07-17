package sabbat.location.cep.flink.activity.model;

import java.time.Instant;

/**
 * Created by bruenni on 08.10.16.
 */
public abstract class TimeSeries {

    private Instant timestamp;

    public TimeSeries() {
    }

    public TimeSeries(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Instant getTimestamp() {
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
