package sabbat.location.cep.flink.activity.model;

import java.math.BigDecimal;

/**
 * Created by bruenni on 15.07.17.
 */
public class ActivityMetric {
    private BigDecimal longitude;
    private BigDecimal latitude;
    private long distance;
    private BigDecimal velocity;
    private BigDecimal avgVelocity;

    /**
     * Constructor
     * @param longitude
     * @param latitude
     * @param distance whole distance since start of activity
     * @param velocity current velocity
     * @param avgVelocity avg velocity since start of activity
     */
    public ActivityMetric(BigDecimal longitude, BigDecimal latitude, long distance, BigDecimal velocity, BigDecimal avgVelocity) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.distance = distance;
        this.velocity = velocity;
        this.avgVelocity = avgVelocity;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public long getDistance() {
        return distance;
    }

    public BigDecimal getVelocity() {
        return velocity;
    }

    public BigDecimal getAvgVelocity() {
        return avgVelocity;
    }

    @Override
    public String toString() {
        return "ActivityMetric{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", distance=" + distance +
                ", velocity=" + velocity +
                ", avgVelocity=" + avgVelocity +
                '}';
    }
}
