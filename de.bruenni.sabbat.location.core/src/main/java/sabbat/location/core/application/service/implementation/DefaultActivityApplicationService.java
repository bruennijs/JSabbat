package sabbat.location.core.application.service.implementation;

import identity.IAuthenticationService;
import identity.UserRef;
import infrastructure.common.event.Event;
import infrastructure.identity.AuthenticationFailedException;
import infrastructure.parser.SerializingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import sabbat.location.core.application.service.ActivityApplicationService;
import sabbat.location.core.application.service.command.ActivityCreateCommand;
import sabbat.location.core.application.service.command.ActivityUpdateCommand;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.ActivityCoordinate;
import sabbat.location.core.domain.model.ActivityCoordinatePrimaryKey;
import sabbat.location.core.persistence.activity.IActivityRepository;

import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bruenni on 24.09.16.
 */
public class DefaultActivityApplicationService implements ActivityApplicationService, ApplicationEventPublisherAware {

    private Logger logger = LoggerFactory.getLogger(DefaultActivityApplicationService.class);

    private IActivityRepository activityRepository;
    private IAuthenticationService authenticationService;
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * Constructor.
     * @param activityRepository
     */
    public DefaultActivityApplicationService(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public Activity start(ActivityCreateCommand command) throws AuthenticationFailedException, SerializingException {

        Instant now = Instant.now(Clock.systemUTC());
        Date nowDate = Date.from(now);
        Activity activity = new Activity(0l, command.getId(), command.getTitle(), nowDate, command.getUser().getId());

        // start activity
        Event domainEvent = activity.start();

        Activity activityPersisted = this.activityRepository.save(activity);

        // fire domain event
        applicationEventPublisher.publishEvent(domainEvent);

        return activityPersisted;
    }

    @Override
    public Void stop(String id) throws Exception {
        throw new Exception("not implemented");
    }

    @Override
    public Iterable<ActivityCoordinate> update(ActivityUpdateCommand command) throws Exception {

        List<ActivityCoordinate> activityCoordinates = toActivityCoordinates(command.getUser(), command);

        return this.activityRepository.insertCoordinate(activityCoordinates);
    }

    private List<ActivityCoordinate> toActivityCoordinates(UserRef userRef, ActivityUpdateCommand command) {
        return command.getCoordinates().stream().map(coordinate -> {

            ActivityCoordinatePrimaryKey pKey = new ActivityCoordinatePrimaryKey(userRef.getId(), command.getActivityId(), coordinate.getTimestamp());

            return new ActivityCoordinate(pKey, coordinate.getLatitude(), coordinate.getLongitude());
        }).collect(Collectors.toList());
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
