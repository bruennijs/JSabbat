package sabbat.location.core.application.service.command;

import java.util.UUID;

/**
 * Created by bruenni on 24.09.16.
 */
public class ActivityStopCommand extends ActivityBase {
    private String id;

    public ActivityStopCommand(String id)
    {
        super(id);
    }
}
