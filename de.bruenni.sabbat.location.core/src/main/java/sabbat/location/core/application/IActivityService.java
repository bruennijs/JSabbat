package sabbat.location.core.application;

import org.reactivestreams.Publisher;

/**
 * Created by bruenni on 04.07.16.
 */
public interface IActivityService {
    Publisher<Activity> start(ActivityCreateCommand command);

    Publisher<Void> stop(String id);

    Publisher<Void> update(ActivityUpdateCommand command);
}
