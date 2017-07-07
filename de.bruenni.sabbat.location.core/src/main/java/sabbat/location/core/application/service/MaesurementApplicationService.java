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
public interface MaesurementApplicationService {

    /**
     * Stores to persistence and executes business logic.
     * @param command
     * @return
     * @throws Exception
     */
    Iterable<UserCoordinate> insert(ActivityUpdateCommand command) throws Exception;
}
