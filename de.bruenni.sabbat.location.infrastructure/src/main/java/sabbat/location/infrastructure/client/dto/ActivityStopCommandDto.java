package sabbat.location.infrastructure.client.dto;

import org.springframework.data.geo.Point;
import sabbat.location.core.application.ActivityBase;

/**
 * Created by bruenni on 04.07.16.
 */
public class ActivityStopCommandDto extends ActivityBase {

    /**
     * Constructor
     */
    public ActivityStopCommandDto(String id) {
        super(id);
    }
}
