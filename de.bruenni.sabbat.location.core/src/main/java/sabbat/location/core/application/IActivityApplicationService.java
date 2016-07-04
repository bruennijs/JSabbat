package sabbat.location.core.application;

import org.reactivestreams.Publisher;

/**
 * Created by bruenni on 04.07.16.
 */
public interface IActivityApplicationService {
    Publisher<ActivityResponse> start(ActivityCreateCommand command);

    Publisher<Response> stop(String id);

    Publisher<Response> update(ActivityUpdateCommand command);
}
