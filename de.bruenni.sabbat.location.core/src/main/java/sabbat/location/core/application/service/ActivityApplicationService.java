package sabbat.location.core.application.service;

import identity.IAuthenticationService;
import identity.UserRef;
import infrastructure.identity.AuthenticationFailedException;
import infrastructure.util.IterableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sabbat.location.core.application.service.command.ActivityCreateCommand;
import sabbat.location.core.application.service.command.ActivityUpdateCommand;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.ActivityCoordinate;
import sabbat.location.core.domain.model.ActivityCoordinatePrimaryKey;
import sabbat.location.core.domain.model.ActivityPrimaryKey;
import sabbat.location.core.persistence.activity.IActivityRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bruenni on 24.09.16.
 */
public class ActivityApplicationService implements IActivityApplicationService {

    private Logger logger = LoggerFactory.getLogger(ActivityApplicationService.class);

    private IActivityRepository activityRepository;
    private IAuthenticationService authenticationService;

    /**
     * Constructor.
     * @param activityRepository
     * @param authenticationService
     */
    public ActivityApplicationService(IActivityRepository activityRepository, IAuthenticationService authenticationService) {
        this.activityRepository = activityRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public Activity start(ActivityCreateCommand command) throws AuthenticationFailedException {

        // verify token
        UserRef userRef = this.authenticationService.verify(command.getIdentityToken());

        Activity domainObject = new Activity(new ActivityPrimaryKey(userRef.getId(), command.getId()), command.getTitle(), new Date());
        return this.activityRepository.save(domainObject);
    }

    @Override
    public Void stop(String id) throws Exception {
        throw new Exception("not implemented");
    }

    @Override
    public Iterable<ActivityCoordinate> update(ActivityUpdateCommand command) throws Exception {

        UserRef userRef = this.authenticationService.verify(command.getIdentityToken());

        List<ActivityCoordinate> activityCoordinates = toActivityCoordinates(userRef, command);

        return this.activityRepository.insertCoordinate(activityCoordinates);
    }

    private List<ActivityCoordinate> toActivityCoordinates(UserRef userRef, ActivityUpdateCommand command) {
        return command.getCoordinates().stream().map(coordinate -> {

            ActivityCoordinatePrimaryKey pKey = new ActivityCoordinatePrimaryKey(userRef.getId(), command.getActivityId(), coordinate.getTimestamp());

            return new ActivityCoordinate(pKey, coordinate.getLatitude(), coordinate.getLongitude());
        }).collect(Collectors.toList());
    }
}
