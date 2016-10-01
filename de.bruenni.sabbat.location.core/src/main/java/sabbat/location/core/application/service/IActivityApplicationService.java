package sabbat.location.core.application.service;

import org.springframework.util.concurrent.ListenableFuture;
import sabbat.location.core.application.service.command.ActivityCreateCommand;
import sabbat.location.core.application.service.command.ActivityUpdateCommand;
import sabbat.location.core.domain.model.Activity;

/**
 * Created by bruenni on 04.07.16.
 */
public interface IActivityApplicationService {
    Activity start(ActivityCreateCommand command);

    Void stop(String id) throws Exception;

    ListenableFuture<Void> update(ActivityUpdateCommand command) throws Exception;
}
