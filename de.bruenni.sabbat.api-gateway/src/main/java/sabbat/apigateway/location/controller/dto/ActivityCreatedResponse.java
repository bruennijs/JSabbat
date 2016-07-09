package sabbat.apigateway.location.controller.dto;

/**
 * Created by bruenni on 09.07.16.
 */
public class ActivityCreatedResponse extends MapMyTracksResponse {
    private String activityId;

    public ActivityCreatedResponse(String activityId) {
        super("activity_started");
        this.activityId = activityId;
    }

    public String getActivityId() {
        return activityId;
    }
}
