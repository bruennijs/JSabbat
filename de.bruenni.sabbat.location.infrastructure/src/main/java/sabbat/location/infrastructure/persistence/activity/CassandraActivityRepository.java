package sabbat.location.infrastructure.persistence.activity;

import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.CassandraRepository;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.persistence.activity.ActivityPrimaryKey;
import sabbat.location.core.persistence.activity.IActivityRepository;

/**
 * Created by bruenni on 13.09.16.
 */
public class CassandraActivityRepository<S extends Activity> implements IActivityRepository {

    private CassandraTemplate template;

    public CassandraActivityRepository(CassandraTemplate template) {
        this.template = template;
    }

    @Override
    public Activity save(Activity entity) {
        return null;
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
