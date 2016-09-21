package sabbat.location.infrastructure.integrationtest.persistence.activity;

import sabbat.location.core.domain.model.Coordinate;

/**
 * Created by bruenni on 21.09.16.
 */
public class CoordinateBuilder {
    public Coordinate build() {
        return new Coordinate(0.0f, 0.0f);
    }
}
