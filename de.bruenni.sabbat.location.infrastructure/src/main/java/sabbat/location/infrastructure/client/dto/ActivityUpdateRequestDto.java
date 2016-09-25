package sabbat.location.infrastructure.client.dto;

import org.springframework.data.geo.Point;

/**
 * Created by bruenni on 04.07.16.
 */
public class ActivityUpdateRequestDto extends ActivityDtoBase {

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
