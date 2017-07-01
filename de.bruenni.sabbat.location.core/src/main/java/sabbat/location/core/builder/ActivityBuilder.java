package sabbat.location.core.builder;

import sabbat.location.core.domain.model.Activity;

import java.util.Date;
import java.util.UUID;

/**
 * Created by bruenni on 20.09.16.
 */
public class ActivityBuilder {

    private static Long idCounter = 1l;
    private String userId = UUID.randomUUID().toString();
    private String title = "RuK 2017";
    private String uuid = UUID.randomUUID().toString();

    public Activity build() {
        return new Activity(getNextid(),
            uuid,
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

    public ActivityBuilder withUuid(String value) {
        this.uuid = value;
        return this;
    }
}
