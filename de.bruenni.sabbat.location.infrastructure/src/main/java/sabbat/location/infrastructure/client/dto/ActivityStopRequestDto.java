package sabbat.location.infrastructure.client.dto;

import org.springframework.data.geo.Point;
import sabbat.location.core.application.ActivityBase;

/**
 * Created by bruenni on 04.07.16.
 */
public class ActivityStopRequestDto extends ActivityDtoBase {

    /**
     * Constructor
     */
    public ActivityStopRequestDto(String id) {
        super(id);
    }
}
