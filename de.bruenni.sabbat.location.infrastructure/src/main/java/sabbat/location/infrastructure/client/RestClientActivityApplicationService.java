package sabbat.location.infrastructure.client;

import org.reactivestreams.Publisher;
import sabbat.location.core.application.*;

/**
 * Created by bruenni on 04.07.16.
 */
public class RestClientActivityApplicationService implements IActivityApplicationService {
    @Override
    public Publisher<ActivityResponse> start(ActivityCreateCommand command) {
        return null;
    }

    @Override
    public Publisher<Response> stop(String id) {
        return null;
    }

    @Override
    public Publisher<Response> update(ActivityUpdateCommand command) {
        return null;
    }
}
