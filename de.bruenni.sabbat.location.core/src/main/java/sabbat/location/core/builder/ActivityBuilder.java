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
    private String title = "RuK 2017";

    public Activity build() {
        return new Activity(getNextid(),
            UUID.randomUUID().toString(),
            title,
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

    public ActivityBuilder withTitle(String value) {
        this.title = value;
        return this;
    }
}
