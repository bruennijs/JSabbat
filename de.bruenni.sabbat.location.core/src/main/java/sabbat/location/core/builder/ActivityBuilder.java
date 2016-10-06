package sabbat.location.core.builder;

import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.ActivityPrimaryKey;

import java.util.Date;
import java.util.UUID;

/**
 * Created by bruenni on 20.09.16.
 */
public class ActivityBuilder {
    public Activity build()
    {
        return new Activity(new ActivityPrimaryKey(UUID.randomUUID().toString(), UUID.randomUUID().toString()), "mein erstes rennen in Köln", new Date());
    }
}
