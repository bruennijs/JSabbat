package sabbat.location.core.persistence.activity.implementation;

import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.ActivityCoordinate;
import sabbat.location.core.domain.model.ActivityPrimaryKey;
import sabbat.location.core.persistence.activity.IActivityRepository;

import java.util.List;

/**
 * Created by bruenni on 10.10.16.
 */
public class ActivityRepositoryDummy implements IActivityRepository {
    @Override
    public Iterable<ActivityCoordinate> insertCoordinate(List<ActivityCoordinate> coordinate) {
        return null;
    }

    @Override
    public Iterable<ActivityCoordinate> findActivityCoordinates(Activity aggregateRoot) {
        return null;
    }

    @Override
    public Activity save(Activity entity) {
        return entity;
    }

    @Override
    public <S extends Activity> Iterable<S> save(Iterable<S> entities) {
        return null;
    }

    @Override
    public Activity findOne(ActivityPrimaryKey activityPrimaryKey) {
        return null;
    }

    @Override
    public boolean exists(ActivityPrimaryKey activityPrimaryKey) {
        return false;
    }

    @Override
    public Iterable<Activity> findAll() {
        return null;
    }

    @Override
    public Iterable<Activity> findAll(Iterable<ActivityPrimaryKey> activityPrimaryKeys) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(ActivityPrimaryKey activityPrimaryKey) {

    }

    @Override
    public void delete(Activity entity) {

    }

    @Override
    public void delete(Iterable<? extends Activity> entities) {

    }

    @Override
    public void deleteAll() {

    }
}