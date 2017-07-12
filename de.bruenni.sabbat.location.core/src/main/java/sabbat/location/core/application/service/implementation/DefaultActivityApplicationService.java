package sabbat.location.core.application.service.implementation;

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
import sabbat.location.core.domain.events.activity.ActivityEvent;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.coordinate.UserCoordinate;
import sabbat.location.core.domain.model.coordinate.UserCoordinatePrimaryKey;
import sabbat.location.core.persistence.activity.IActivityRepository;

import java.math.BigDecimal;
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
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * Constructor.
     * @param activityRepository
     */
    public DefaultActivityApplicationService(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public Activity start(ActivityCreateCommand command) throws AuthenticationFailedException, SerializingException {

        Instant now = Instant.now(Clock.systemUTC());
        Date nowDate = Date.from(now);
        Activity activity = new Activity(0l, command.getId(), command.getTitle(), nowDate, command.getUser().getId());

        Activity activityPersisted = this.activityRepository.save(activity);

        // start activity
        Event domainEvent = activityPersisted.start();

        activityPersisted = this.activityRepository.save(activityPersisted);

        // fire domain event
        applicationEventPublisher.publishEvent(domainEvent);

        return activityPersisted;
    }

    @Override
    public void stop(String id) throws Exception {

        logger.debug(String.format("Stopping actitity [uuid=%1s]", id));

        Activity activity = this.activityRepository.findByUuid(id);
        if (activity != null)
        {
            ActivityEvent domainEvent = activity.stop();

            this.activityRepository.save(activity);

            applicationEventPublisher.publishEvent(domainEvent);
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
