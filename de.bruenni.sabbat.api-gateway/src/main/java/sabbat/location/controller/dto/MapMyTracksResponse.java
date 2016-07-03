package sabbat.location.controller.dto;

/**
 * Created by bruenni on 03.07.16.
 */
public class MapMyTracksResponse {
    private String type;

    public MapMyTracksResponse(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
