package sabbat.location.core.application.service.command;

import java.util.UUID;

/**
 * Created by bruenni on 04.07.16.
 */
public abstract class ActivityBase {

    private UUID id;

    protected ActivityBase(UUID id) {
        this.id = id;
    }

    /**
     * Gets the ID of the activity
     * @return
     */
    public UUID getId() {
        return id;
    }
}
