package sabbat.location.core.application;

/**
 * Created by bruenni on 04.07.16.
 */
public class ActivityCreateCommand {
    private String title;

    public ActivityCreateCommand(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
