package sabbat.location.core.builder;

import sabbat.location.core.domain.model.ActivityCoordinatePrimaryKey;
import sabbat.location.core.domain.model.ActivityPrimaryKey;

import java.util.Date;

/**
 * Created by bruenni on 21.09.16.
 */
public class ActivityCoordinatePrimaryKeyBuilder {
    public ActivityCoordinatePrimaryKey fromActivityKey(ActivityPrimaryKey activityKey, Date timestamp) {

        return new ActivityCoordinatePrimaryKey(activityKey.getUserId(),activityKey.getId(), timestamp);
    }
}
