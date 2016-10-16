package sabbat.location.core.application.service.command;

import infrastructure.identity.Token;

import java.util.UUID;

/**
 * Created by bruenni on 04.07.16.
 */
public class ActivityCreateCommand extends ActivityBase {
    private Token identityToken;
    private String title;

    public ActivityCreateCommand(Token identityToken, String activityId, String title) {
        super(activityId);
        this.identityToken = identityToken;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Token getIdentityToken() {
        return identityToken;
    }
}
