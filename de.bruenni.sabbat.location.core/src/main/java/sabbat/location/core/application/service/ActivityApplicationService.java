package sabbat.location.core.application.service;

import infrastructure.common.event.IEventHandler;
import infrastructure.identity.AuthenticationFailedException;
import infrastructure.parser.SerializingException;
import sabbat.location.core.application.service.command.ActivityCreateCommand;
import sabbat.location.core.application.service.command.ActivityUpdateCommand;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.ActivityCoordinate;

import java.util.List;

/**
 * Created by bruenni on 04.07.16.
 */
public interface ActivityApplicationService {
    /**
     * Starts a activity.
     * @param command
     * @return
     */
    Activity start(ActivityCreateCommand command) throws AuthenticationFailedException, SerializingException;

    /**
     * Stops a activity.
     * @param id
     * @return
     * @throws Exception
     */
    void stop(String id) throws Exception;

    /**
     * Stores to persistence and executes business logic.
     * @param command
     * @return
     * @throws Exception
     */
    Iterable<ActivityCoordinate> update(ActivityUpdateCommand command) throws Exception;
}
