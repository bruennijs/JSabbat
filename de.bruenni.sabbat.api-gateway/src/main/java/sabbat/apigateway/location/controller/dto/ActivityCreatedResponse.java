package sabbat.apigateway.location.controller.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by bruenni on 09.07.16.
 */
@JacksonXmlRootElement(localName = "message")
public class ActivityCreatedResponse extends MapMyTracksResponse {
    @JacksonXmlProperty(localName = "activity_id")
    public String activityId;

    public ActivityCreatedResponse(String activityId) {
        super("activity_started");
        this.activityId = activityId;
    }
}
