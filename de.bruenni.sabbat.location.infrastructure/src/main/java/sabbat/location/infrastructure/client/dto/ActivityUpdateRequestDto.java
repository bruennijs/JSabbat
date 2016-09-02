package sabbat.location.infrastructure.client.dto;

import org.springframework.data.geo.Point;
import sabbat.location.core.application.ActivityBase;

/**
 * Created by bruenni on 04.07.16.
 */
public class ActivityUpdateRequestDto extends ActivityBase {

    private Point[] points;

    /**
     * Constructor
     */
    public ActivityUpdateRequestDto(String id, Point[] points) {
        super(id);
        this.points = points;
    }

    public Point[] getPoints() {
        return points;
    }
}