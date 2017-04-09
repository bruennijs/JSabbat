package sabbat.location.core.application.service.implementation;

import identity.IAuthenticationService;
import identity.UserRef;
import infrastructure.common.event.IDomainEventBus;
import infrastructure.common.event.IEvent;
import infrastructure.identity.AuthenticationFailedException;
import infrastructure.parser.SerializingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sabbat.location.core.application.service.IActivityApplicationService;
import sabbat.location.core.application.service.command.ActivityCreateCommand;
import sabbat.location.core.application.service.command.ActivityUpdateCommand;
import sabbat.location.core.domain.events.ActivityStartedEvent;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.ActivityCoordinate;
import sabbat.location.core.domain.model.ActivityCoordinatePrimaryKey;
import sabbat.location.core.domain.model.ActivityPrimaryKey;
import sabbat.location.core.persistence.activity.IActivityRepository;

import java.lang.reflect.Type;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bruenni on 24.09.16.
 */
public class DefaultActivityApplicationService implements IActivityApplicationService {

    private Logger logger = LoggerFactory.getLogger(DefaultActivityApplicationService.class);

    private IActivityRepository activityRepository;
    private IAuthenticationService authenticationService;
    private IDomainEventBus domainEventBus;

    /**
     * Constructor.
     * @param activityRepository
     * @param authenticationService
     */
    public DefaultActivityApplicationService(IActivityRepository activityRepository, IAuthenticationService authenticationService, IDomainEventBus domainEventBus) {
        this.activityRepository = activityRepository;
        this.authenticationService = authenticationService;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public Activity start(ActivityCreateCommand command) throws AuthenticationFailedException, SerializingException {

        // verify token
        UserRef userRef = this.authenticationService.verify(command.getIdentityToken());

        Instant now = Instant.now(Clock.systemUTC());
        Date nowDate = Date.from(now);
        Activity activity = new Activity(0l, command.getId(), command.getTitle(), nowDate, userRef.getId());

        // start activity
        IEvent domainEvent = activity.start();

        // fire domain event
        domainEventBus.publish(domainEvent);

        return this.activityRepository.save(activity);
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

    @Override
    public void OnEvent(IEvent iEvent) {

    }

    @Override
    public Type[] getSupportedEvents() {
        return new Type[0];
    }
}
