package sabbat.location.infrastructure.client.dto;

import sabbat.location.core.application.ActivityBase;

/**
 * Created by bruenni on 04.07.16.
 */
public class ActivityCreateRequestDto extends ActivityBase {
    private String title;

    public ActivityCreateRequestDto(String id, String title) {
        super(id);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
