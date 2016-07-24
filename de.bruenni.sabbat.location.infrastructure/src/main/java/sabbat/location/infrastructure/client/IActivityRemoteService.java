package sabbat.location.infrastructure.client;

import sabbat.location.infrastructure.client.dto.ActivityCreateCommandDto;
import sabbat.location.infrastructure.client.dto.ActivityCreatedResponseDto;
import sabbat.location.infrastructure.client.dto.ActivityStopCommandDto;
import sabbat.location.infrastructure.client.dto.ActivityUpdateCommandDto;

import java.util.concurrent.Future;

/**
 * Created by bruenni on 04.07.16.
 */
public interface IActivityRemoteService {
    Future<ActivityCreatedResponseDto> start(ActivityCreateCommandDto command);

    Future<Void> stop(ActivityStopCommandDto command);

    /**
     * Update of activity with new geo points, heartrate and so on.
     * @param command
     * @return Future completes after update is send to infrfastructure to deliver measurement value.
     */
    Future<Void> update(ActivityUpdateCommandDto command);
}
