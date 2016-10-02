package sabbat.apigateway.location.controller.dto;

/**
 * Created by bruenni on 09.07.16.
 */
public class ActivityUpdatedResponse extends MapMyTracksResponse {
    public ActivityUpdatedResponse() {
        super("activity_updated");
    }

    @Override
    public String toString() {
        return "ActivityUpdatedResponse{} " + super.toString();
    }
}
