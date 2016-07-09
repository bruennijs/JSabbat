package sabbat.location.core.application;

/**
 * Created by bruenni on 04.07.16.
 */
public class ActivityCreateCommand extends ActivityBase {
    private String title;

    public ActivityCreateCommand(String activityId, String title) {
        super(activityId);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
