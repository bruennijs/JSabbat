package sabbat.location.core.application.service.implementation;

import identity.UserRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sabbat.location.core.application.service.MaesurementApplicationService;
import sabbat.location.core.application.service.command.ActivityUpdateCommand;
import sabbat.location.core.domain.model.coordinate.UserCoordinate;
import sabbat.location.core.domain.model.coordinate.UserCoordinatePrimaryKey;
import sabbat.location.core.persistence.coordinate.UserCoordinateRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bruenni on 24.09.16.
 */
public class DefaultMeasurementApplicationService implements MaesurementApplicationService {

    private Logger logger = LoggerFactory.getLogger(DefaultMeasurementApplicationService.class);

    private UserCoordinateRepository userCoordinateRepository;

    /**
     * Constructor.
     */
    public DefaultMeasurementApplicationService(UserCoordinateRepository userCoordinateRepository) {
        this.userCoordinateRepository = userCoordinateRepository;
    }

    @Override
    public Iterable<UserCoordinate> insert(ActivityUpdateCommand command) throws Exception {

        List<UserCoordinate> activityCoordinates = toActivityCoordinates(command.getUser(), command);

        return this.userCoordinateRepository.save(activityCoordinates);
    }

    private List<UserCoordinate> toActivityCoordinates(UserRef userRef, ActivityUpdateCommand command) {
        return command.getCoordinates().stream().map(coordinate -> {

            UserCoordinatePrimaryKey pKey = new UserCoordinatePrimaryKey(userRef.getId(), command.getActivityId(), coordinate.getTimestamp());

            return new UserCoordinate(pKey, BigDecimal.valueOf(coordinate.getLatitude()), BigDecimal.valueOf(coordinate.getLongitude()));
        }).collect(Collectors.toList());
    }
}
