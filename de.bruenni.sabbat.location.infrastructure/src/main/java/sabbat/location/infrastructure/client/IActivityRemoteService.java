package sabbat.location.infrastructure.client;

import org.reactivestreams.Publisher;
import sabbat.location.infrastructure.client.dto.ActivityCreateCommandDto;
import sabbat.location.infrastructure.client.dto.ActivityCreatedResponseDto;

/**
 * Created by bruenni on 04.07.16.
 */
public interface IActivityRemoteService {
    Publisher<ActivityCreatedResponseDto> start(ActivityCreateCommandDto command);

    Publisher<Void> stop(String id);

    Publisher<Void> update();
}
