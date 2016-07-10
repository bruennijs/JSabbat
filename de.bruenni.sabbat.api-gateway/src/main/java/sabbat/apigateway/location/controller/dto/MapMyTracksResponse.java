package sabbat.apigateway.location.controller.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Created by bruenni on 03.07.16.
 */
public class MapMyTracksResponse {
    @JacksonXmlProperty(localName = "type")
    private String type;

    public MapMyTracksResponse(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
