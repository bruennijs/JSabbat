package sabbat.location.core.persistence.activity;

import org.springframework.data.repository.CrudRepository;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.ActivityCoordinate;
import sabbat.location.core.domain.model.ActivityRelation;

import java.util.List;
import java.util.Set;

/**
 * Created by bruenni on 13.09.16.
 */
public interface IActivityRepository extends CrudRepository<Activity, Long> {

    /***
     * Finds all activities related to a set of users.
     * @param associatedUserIds
     * @return
     */
    Iterable<Activity> findByUserIds(Iterable<String> associatedUserIds) throws Exception;

    /***
     * Finds acivity in state "Started" but not stopped yet of all given user ids.
     * @param userIds
     * @return
     */
    Iterable<Activity> findActiveActivitiesByUserIds(Iterable<String> userIds);

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

    /**
     * Persists the activity relation to database.
     * @param relation
     * @return
     */
    ActivityRelation save(ActivityRelation relation);

    /**
     * refreshes
     * @param torefresh
     */
    void refresh(Activity torefresh);

    /**
     * deletes all entities in table 'activity'
     */
    void truncate();
}
