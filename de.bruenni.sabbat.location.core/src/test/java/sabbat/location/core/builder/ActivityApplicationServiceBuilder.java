package sabbat.location.core.builder;

import sabbat.location.core.application.service.ActivityApplicationService;
import sabbat.location.core.persistence.activity.IActivityRepository;

/**
 * Created by bruenni on 01.10.16.
 */
public class ActivityApplicationServiceBuilder {


    private IActivityRepository repository = new ActivityRepositoryBuilder().buildmocked();

    public ActivityApplicationService build() {
        return new ActivityApplicationService(this.repository);
    }
}
