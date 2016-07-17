package sabbat.location.infrastructure.client.implementations;

import org.reactivestreams.Publisher;
import sabbat.location.core.application.*;
import sabbat.location.infrastructure.client.IActivityRemoteService;
import sabbat.location.infrastructure.client.dto.ActivityCreateCommandDto;
import sabbat.location.infrastructure.client.dto.ActivityCreatedResponseDto;

/**
 * Created by bruenni on 04.07.16.
 */
public class RabbitMqClientActivityApplicationService implements IActivityRemoteService {

    @Override
    public Publisher<ActivityCreatedResponseDto> start(ActivityCreateCommandDto command) {
        return null;
    }

    @Override
    public Publisher<Void> stop(String id) {
        return null;
    }

    @Override
    public Publisher<Void> update() {
        return null;
    }
}
