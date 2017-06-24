package sabbat.location.core.application.service.command;

import identity.UserRef;
import infrastructure.identity.Token;

import java.util.List;
import java.util.Optional;

/**
 * Created by bruenni on 04.07.16.
 */
public class ActivityUpdateCommand {

    private UserRef user;
    private String activityId;
    private List<TimeCoordinate> coordinates;
    private Optional<Integer> heartRate;
    private Optional<Integer> cadence;

    /**
     * Constructor
     */
    public ActivityUpdateCommand(UserRef user,
                                 String activityId,
                                 List<TimeCoordinate> coordinates,
                                 Optional<Integer> heartRate,
                                 Optional<Integer> cadence) {
        this.user = user;
        this.activityId = activityId;
        this.coordinates = coordinates;
        this.heartRate = heartRate;
        this.cadence = cadence;
    }

    public Optional<Integer> getHeartRate() {
        return heartRate;
    }

    public Optional<Integer> getCadence() {
        return cadence;
    }

    public List<TimeCoordinate> getCoordinates() {
        return coordinates;
    }

    public String getActivityId() {
        return activityId;
    }

    public UserRef getUser() {
        return user;
    }
}
