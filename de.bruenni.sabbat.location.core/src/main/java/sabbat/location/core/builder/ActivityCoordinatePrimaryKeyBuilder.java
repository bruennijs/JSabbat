package sabbat.location.core.builder;

import sabbat.location.core.domain.model.coordinate.UserCoordinatePrimaryKey;
import sabbat.location.core.domain.model.ActivityPrimaryKey;

import java.util.Date;

/**
 * Created by bruenni on 21.09.16.
 */
public class ActivityCoordinatePrimaryKeyBuilder {
    public UserCoordinatePrimaryKey fromActivityKey(ActivityPrimaryKey activityKey, Date timestamp) {

        return new UserCoordinatePrimaryKey(activityKey.getUserId().toString(), activityKey.getId().toString(), timestamp);
    }
}
