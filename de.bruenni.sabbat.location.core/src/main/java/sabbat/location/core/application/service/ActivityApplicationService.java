package sabbat.location.core.application.service;

import org.springframework.util.concurrent.ListenableFuture;
import sabbat.location.core.application.service.command.ActivityCreateCommand;
import sabbat.location.core.application.service.command.ActivityStopCommand;
import sabbat.location.core.application.service.command.ActivityUpdateCommand;
import sabbat.location.core.domain.model.Activity;

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
        return null;
    }

    @Override
    public ListenableFuture<Void> stop(String id) {
        return null;
    }

    @Override
    public ListenableFuture<Void> update(ActivityUpdateCommand command) {
        return null;
    }
}
