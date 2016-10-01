package sabbat.location.core.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import sabbat.location.core.application.service.command.ActivityCreateCommand;
import sabbat.location.core.application.service.command.ActivityStopCommand;
import sabbat.location.core.application.service.command.ActivityUpdateCommand;
import sabbat.location.core.domain.model.Activity;
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

    public ActivityApplicationService(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public Activity start(ActivityCreateCommand command) {
        Activity domainObject = new Activity(new ActivityPrimaryKey(command.getUserId(), command.getId()), command.getTitle(), new Date());
        return this.activityRepository.save(domainObject);
    }

    @Override
    public Void stop(String id) throws Exception {
        throw new Exception("not implemented");
    }

    @Override
    public ListenableFuture<Void> update(ActivityUpdateCommand command) throws Exception {
        throw new Exception("not implemented");
    }
}
