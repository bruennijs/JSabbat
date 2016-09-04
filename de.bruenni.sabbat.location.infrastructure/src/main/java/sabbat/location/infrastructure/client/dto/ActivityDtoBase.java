package sabbat.location.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bruenni on 04.07.16.
 */
public abstract class ActivityDtoBase {

    private String id;

    protected ActivityDtoBase(String id) {
        this.id = id;
    }

    /**
     * Gets the ID of the activity
     * @return
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }
}
