package sabbat.location.cep.flink.activity.model;

import infrastructure.tracking.Metrics;
import infrastructure.tracking.distance.Distance;

import java.math.BigDecimal;
import java.time.Duration;

/**
 * Created by bruenni on 15.07.17.
 * Delta of distance and duration between two points and corrdinates in time.
 */
public class DeltaV {
    private Distance<BigDecimal> distance;
    private Duration duration;

    /**
     *
     * @param distance
     * @param duration
     */
    public DeltaV(Distance<BigDecimal> distance, Duration duration) {
        this.distance = distance;
        this.duration = duration;
    }

    public Distance<BigDecimal> getDistance() {
        return distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public DeltaV plus(DeltaV summand) throws Exception {

        this.distance = this.distance.plus(summand.getDistance());
        this.duration = this.duration.plus(summand.getDuration());
        return this;
    }

    public static DeltaV empty() {
        return new DeltaV(Distance.from(BigDecimal.ZERO, Metrics.CENTIMETER), Duration.ZERO);
    }
}
