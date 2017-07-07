package sabbat.location.core.persistence.activity;

import org.springframework.data.repository.CrudRepository;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.coordinate.UserCoordinate;
import sabbat.location.core.domain.model.ActivityRelation;

import java.util.List;

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
    Iterable<Activity> findActiveActivitiesByUserIds(List<String> userIds);

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

    /**
     * Finds activity by UUID.
     * @param uuid
     * @return
     */
    Activity findByUuid(String uuid);
}
