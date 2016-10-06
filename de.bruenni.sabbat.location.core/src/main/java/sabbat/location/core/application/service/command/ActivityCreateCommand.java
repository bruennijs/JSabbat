package sabbat.location.core.application.service.command;

import java.util.UUID;

/**
 * Created by bruenni on 04.07.16.
 */
public class ActivityCreateCommand extends ActivityBase {
    private String title;
    private String userId;

    public ActivityCreateCommand(String userId, String activityId, String title) {
        super(activityId);
        this.userId = userId;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getUserId() {
        return userId;
    }
}
