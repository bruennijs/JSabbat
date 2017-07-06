package sabbat.location.core.builder;

import sabbat.location.core.domain.model.coordinate.UserCoordinate;
import sabbat.location.core.domain.model.coordinate.UserCoordinatePrimaryKey;

import java.math.BigDecimal;

/**
 * Created by bruenni on 21.09.16.
 */
public class UserCoordinateBuilder {
    private UserCoordinatePrimaryKey pkey;

    public UserCoordinate build() {
        return new UserCoordinate(this.pkey, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public UserCoordinateBuilder withPrimaryKey(UserCoordinatePrimaryKey value) {
        pkey = value;
        return this;
    }
}
