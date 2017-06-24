package sabbat.location.core.application.service.command;

import identity.UserRef;
import infrastructure.identity.Token;

import java.util.UUID;

/**
 * Created by bruenni on 04.07.16.
 */
public class ActivityCreateCommand extends ActivityBase {
    private UserRef user;
    private String title;

    public ActivityCreateCommand(UserRef user, String activityId, String title) {
        super(activityId);
        this.user = user;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public UserRef getUser() {
        return user;
    }

    public void setUser(UserRef user)
    {
        this.user = user;
    }
}
