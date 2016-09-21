package sabbat.location.infrastructure.integrationtest.persistence.activity;

import sabbat.location.core.domain.model.ActivityCoordinatePrimaryKey;
import sabbat.location.core.domain.model.ActivityPrimaryKey;

import java.util.Date;

/**
 * Created by bruenni on 21.09.16.
 */
public class ActivityCoordinatePrimaryKeyBuilder {
    private Date timestamp = new Date();

    public ActivityCoordinatePrimaryKey fromActivityKey(ActivityPrimaryKey activityKey) {

        return new ActivityCoordinatePrimaryKey(activityKey.getUserId(),activityKey.getId(), timestamp);
    }
}
