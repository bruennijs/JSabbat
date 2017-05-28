package sabbat.location.core.builder;

import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.ActivityPrimaryKey;

import java.util.Date;
import java.util.UUID;

/**
 * Created by bruenni on 20.09.16.
 */
public class ActivityBuilder {

    private static Long idCounter = 1l;

    public Activity build() {
        return new Activity(getNextid(), UUID.randomUUID().toString(), "mein erstes rennen in Köln", new Date(), UUID.randomUUID().toString());
    }

    public Long getNextid() {
        idCounter++;
        return idCounter;
    }
}
