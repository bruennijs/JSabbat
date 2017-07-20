package sabbat.location.cep.flink.activity.model;

import infrastructure.tracking.distance.Distance;

import java.math.BigDecimal;
import java.time.Duration;

/**
 * Created by bruenni on 18.07.17.
 */
public class ActivitySum {
    private Distance<BigDecimal> distance;
    private Duration duration;

    public ActivitySum(Distance<BigDecimal> distance, Duration duration) {
        this.distance = distance;
        this.duration = duration;
    }

    public Distance<BigDecimal> getDistance() {
        return distance;
    }

    public void setDistance(Distance<BigDecimal> distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivitySum that = (ActivitySum) o;

        if (!distance.equals(that.distance)) return false;
        return duration.equals(that.duration);
    }

    @Override
    public int hashCode() {
        int result = distance.hashCode();
        result = 31 * result + duration.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ActivitySum{" +
                "distance=" + distance +
                ", duration=" + duration +
                '}';
    }
}
