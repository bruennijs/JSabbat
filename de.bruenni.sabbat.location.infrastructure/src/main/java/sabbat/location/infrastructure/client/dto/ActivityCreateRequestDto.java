package sabbat.location.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bruenni on 04.07.16.
 */
public class ActivityCreateRequestDto extends ActivityDtoBase {
    private String title;

    public ActivityCreateRequestDto() {
        super();
    }

    public ActivityCreateRequestDto(String id, String title) {
        super(id);
        this.title = title;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }
}
