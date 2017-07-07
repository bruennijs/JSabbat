package sabbat.location.core.persistence.coordinate;

import org.springframework.data.repository.CrudRepository;
import sabbat.location.core.domain.model.coordinate.UserCoordinate;
import sabbat.location.core.domain.model.coordinate.UserCoordinatePrimaryKey;

/**
 * Created by bruenni on 06.07.17.
 */
public interface UserCoordinateRepository extends CrudRepository<UserCoordinate, UserCoordinatePrimaryKey> {

    /**
     * Finds all coordinates by activity (no clause on captured field)
     * @param userid
     * @param activityId
     * @return
     */
    Iterable<UserCoordinate> findActivityCoordinates(String userid, String activityId);
}
