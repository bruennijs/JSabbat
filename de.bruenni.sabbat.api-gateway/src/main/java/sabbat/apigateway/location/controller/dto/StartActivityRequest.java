package sabbat.apigateway.location.controller.dto;

/**
 * Created by bruenni on 03.07.16.
 */
public class StartActivityRequest extends Request {
    private String[] tags;

    public StartActivityRequest(String request, String[] tags) {
        super(request);
        this.tags = tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String[] getTags() {
        return tags;
    }
}
