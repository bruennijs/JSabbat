package sabbat.location.core.application.service;

import infrastructure.identity.AuthenticationFailedException;
import infrastructure.parser.SerializingException;
import sabbat.location.core.application.service.command.ActivityCreateCommand;
import sabbat.location.core.application.service.command.ActivityUpdateCommand;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.coordinate.UserCoordinate;

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
}
