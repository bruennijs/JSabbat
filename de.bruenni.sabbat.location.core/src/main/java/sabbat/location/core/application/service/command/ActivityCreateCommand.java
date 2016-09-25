package sabbat.location.core.application.service.command;

import java.util.UUID;

/**
 * Created by bruenni on 04.07.16.
 */
public class ActivityCreateCommand extends ActivityBase {
    private String title;

    public ActivityCreateCommand(UUID activityId, String title) {
        super(activityId);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
