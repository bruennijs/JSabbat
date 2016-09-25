package sabbat.location.core.application.service;

import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import sabbat.location.core.application.service.command.ActivityCreateCommand;
import sabbat.location.core.application.service.command.ActivityStopCommand;
import sabbat.location.core.application.service.command.ActivityUpdateCommand;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.ActivityPrimaryKey;

import java.util.Date;
import java.util.UUID;

/**
 * Created by bruenni on 24.09.16.
 */
public class ActivityApplicationService implements IActivityApplicationService {
    public ListenableFuture<Activity> startActivity(ActivityCreateCommand command) throws Exception {
        throw new Exception("not implemnted");
    }

    public ListenableFuture<Activity> stopActivity(ActivityStopCommand command) throws Exception {
        throw new Exception("not implemnted");
    }

    @Override
    public ListenableFuture<Activity> start(ActivityCreateCommand command) {
        return new AsyncResult(new Activity(new ActivityPrimaryKey(command.getId(), command.getId()), command.getTitle(), new Date()));
    }

    @Override
    public ListenableFuture<Void> stop(String id) throws Exception {
        throw new Exception("not implemented");
    }

    @Override
    public ListenableFuture<Void> update(ActivityUpdateCommand command) throws Exception {
        throw new Exception("not implemented");
    }
}
