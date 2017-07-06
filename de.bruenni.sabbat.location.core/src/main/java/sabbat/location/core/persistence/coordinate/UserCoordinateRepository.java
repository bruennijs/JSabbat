package sabbat.location.core.persistence.coordinate;

import org.springframework.data.repository.CrudRepository;
import sabbat.location.core.domain.model.coordinate.UserCoordinate;
import sabbat.location.core.domain.model.coordinate.UserCoordinatePrimaryKey;

/**
 * Created by bruenni on 06.07.17.
 */
public interface UserCoordinateRepository extends CrudRepository<UserCoordinate, UserCoordinatePrimaryKey> {
}
