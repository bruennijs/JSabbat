package sabbat.location.core.application.service.command;

import org.springframework.data.geo.Point;
import sabbat.location.core.domain.model.ActivityCoordinate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by bruenni on 04.07.16.
 */
public class ActivityUpdateCommand {

    private List<ActivityCoordinate> coordinates;
    private Optional<Integer> heartRate;
    private Optional<Integer> cadence;

    /**
     * Constructor
     */
    public ActivityUpdateCommand(List<ActivityCoordinate> coordinates,
                                 Optional<Integer> heartRate,
                                 Optional<Integer> cadence) {
        this.coordinates = coordinates;
        this.heartRate = heartRate;
        this.cadence = cadence;
    }

    public List<ActivityCoordinate> getCoordinates() {
        return coordinates;
    }

    public Optional<Integer> getHeartRate() {
        return heartRate;
    }

    public Optional<Integer> getCadence() {
        return cadence;
    }
}
