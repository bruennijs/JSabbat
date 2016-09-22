package sabbat.location.core.persistence.activity;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.ActivityCoordinate;
import sabbat.location.core.domain.model.ActivityPrimaryKey;

import java.util.List;

/**
 * Created by bruenni on 13.09.16.
 */
public interface IActivityRepository extends CrudRepository<Activity, ActivityPrimaryKey> {
    /**
     * Inserts coordinate time series entry as composition of Activity aggregate.
     * Batch insert of all coordinates.
     * @param coordinate
     * @return
     */
    Iterable<ActivityCoordinate> insertCoordinate(List<ActivityCoordinate> coordinate);

    /***
     * Gets all coordinates of an activity by its aggregate root
     * @param aggregateRoot
     * @return
     */
    Iterable<ActivityCoordinate> findActivityCoordinates(Activity aggregateRoot);
}