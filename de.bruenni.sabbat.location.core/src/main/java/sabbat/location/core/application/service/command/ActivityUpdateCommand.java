package sabbat.location.core.application.service.command;

import org.springframework.data.geo.Point;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by bruenni on 04.07.16.
 */
public class ActivityUpdateCommand extends ActivityBase {

    private Point[] points;
    private Optional<Integer> heartRate;
    private Optional<Integer> cadence;

    /**
     * Constructor
     * @param id
     */
    public ActivityUpdateCommand(String id, Point[] points,
                                 Optional<Integer> heartRate,
                                 Optional<Integer> cadence) {
        super(id);
        this.points = points;
        this.heartRate = heartRate;
        this.cadence = cadence;
    }

    public Point[] getPoints() {
        return points;
    }

    public Optional<Integer> getHeartRate() {
        return heartRate;
    }

    public Optional<Integer> getCadence() {
        return cadence;
    }
}
