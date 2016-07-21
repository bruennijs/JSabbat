package sabbat.location.infrastructure.client.dto;

import sabbat.location.core.application.ActivityBase;

/**
 * Created by bruenni on 04.07.16.
 */
public class ActivityCreateCommandDto extends ActivityBase {
    private String title;

    public ActivityCreateCommandDto(String activityId, String title) {
        super(activityId);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}