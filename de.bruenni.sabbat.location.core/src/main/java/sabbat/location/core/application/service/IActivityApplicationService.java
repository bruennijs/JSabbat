package sabbat.location.core.application.service;

import infrastructure.identity.AuthenticationFailedException;
import org.springframework.util.concurrent.ListenableFuture;
import sabbat.location.core.application.service.command.ActivityCreateCommand;
import sabbat.location.core.application.service.command.ActivityUpdateCommand;
import sabbat.location.core.domain.model.Activity;

/**
 * Created by bruenni on 04.07.16.
 */
public interface IActivityApplicationService {
    /**
     * Starts a activity.
     * @param command
     * @return
     */
    Activity start(ActivityCreateCommand command) throws AuthenticationFailedException;

    /**
     * Stops a activity.
     * @param id
     * @return
     * @throws Exception
     */
    Void stop(String id) throws Exception;

    /**
     * Stores to persistence and executes business logic.
     * @param command
     * @return
     * @throws Exception
     */
    void update(ActivityUpdateCommand command) throws Exception;
}
