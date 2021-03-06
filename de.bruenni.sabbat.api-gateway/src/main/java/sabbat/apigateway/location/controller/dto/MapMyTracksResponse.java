package sabbat.apigateway.location.controller.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Created by bruenni on 03.07.16.
 */
public class MapMyTracksResponse {
    @JacksonXmlProperty(localName = "type")
    public String type;

    public MapMyTracksResponse(String type)
    {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MapMyTracksResponse{" +
                "type='" + type + '\'' +
                '}';
    }
}
