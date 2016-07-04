package sabbat.location.core.application;

/**
 * Created by bruenni on 04.07.16.
 */
public class ActivityResponse {
    private Activity activity;

    public ActivityResponse(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }
}
