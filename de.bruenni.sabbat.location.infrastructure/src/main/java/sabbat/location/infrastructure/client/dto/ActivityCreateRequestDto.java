package sabbat.location.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import sabbat.location.core.application.ActivityBase;

/**
 * Created by bruenni on 04.07.16.
 */
public class ActivityCreateRequestDto extends ActivityDtoBase {
    private String title;

    public ActivityCreateRequestDto(String id, String title) {
        super(id);
        this.title = title;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }
}
