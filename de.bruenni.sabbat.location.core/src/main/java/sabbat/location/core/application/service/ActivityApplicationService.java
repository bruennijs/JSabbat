package sabbat.location.core.application.service;

import identity.IAuthenticationService;
import identity.UserRef;
import infrastructure.identity.AuthenticationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import sabbat.location.core.application.service.command.ActivityCreateCommand;
import sabbat.location.core.application.service.command.ActivityStopCommand;
import sabbat.location.core.application.service.command.ActivityUpdateCommand;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.ActivityCoordinate;
import sabbat.location.core.domain.model.ActivityPrimaryKey;
import sabbat.location.core.persistence.activity.IActivityRepository;

import java.util.Date;
import java.util.UUID;

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

        Activity domainObject = new Activity(new ActivityPrimaryKey(userRef.getName(), command.getId()), command.getTitle(), new Date());
        return this.activityRepository.save(domainObject);
    }

    @Override
    public Void stop(String id) throws Exception {
        throw new Exception("not implemented");
    }

    @Override
    public void update(ActivityUpdateCommand command) throws Exception {
        this.activityRepository.insertCoordinate(command.getCoordinates());
    }
}
