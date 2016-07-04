package sabbat.apigateway.location.controller.dto;

/**
 * Created by bruenni on 03.07.16.
 */
public class StartActivityResponse extends MapMyTracksResponse {
    private String activityId;

    public StartActivityResponse(String activityId) {
        super("activity_started");
        this.activityId = activityId;
    }

    public String getActivityId() {
        return activityId;
    }
}
