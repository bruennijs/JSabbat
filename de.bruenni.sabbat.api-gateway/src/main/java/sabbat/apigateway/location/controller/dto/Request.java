package sabbat.apigateway.location.controller.dto;

/**
 * Created by bruenni on 03.07.16.
 */
public class Request {
    private String request;

    public Request(String request) {
        this.request = request;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
