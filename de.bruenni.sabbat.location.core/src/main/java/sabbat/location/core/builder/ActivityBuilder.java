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
    private String userId = UUID.randomUUID().toString();

    public Activity build() {
        return new Activity(getNextid(),
            UUID.randomUUID().toString(),
            "mein erstes rennen in Köln",
            new Date(),
            userId);
    }

    public Long getNextid() {
        idCounter++;
        return idCounter;
    }

    public ActivityBuilder withUserId(String userId) {
        this.userId = userId;
        return this;
    }
}
