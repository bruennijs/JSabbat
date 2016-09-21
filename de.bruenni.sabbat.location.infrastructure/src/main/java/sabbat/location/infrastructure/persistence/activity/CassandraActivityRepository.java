package sabbat.location.infrastructure.persistence.activity;

import com.sun.javafx.binding.StringFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cassandra.core.ConsistencyLevel;
import org.springframework.cassandra.core.RetryPolicy;
import org.springframework.cassandra.core.WriteOptions;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.CassandraRepository;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.persistence.activity.ActivityPrimaryKey;
import sabbat.location.core.persistence.activity.IActivityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by bruenni on 13.09.16.
 */
public class CassandraActivityRepository<S extends Activity> implements IActivityRepository {

    private Logger log = LoggerFactory.getLogger(CassandraActivityRepository.class);

    private CassandraTemplate template;

    @Value("${cassandra.columnfamily.activity.consistencylevel}")
    public ConsistencyLevel ConsistencyLevel;

    /**
     * Constructor
     * @param template
     */
    public CassandraActivityRepository(CassandraTemplate template) {
        this.template = template;
    }

    @Override
    public Activity save(Activity entity) {

        log.debug(StringFormatter.format("INSERT into activity [%1s, ConsistencyLevel=%2s]", entity.toString(), this.ConsistencyLevel.toString()).getValue());

        return template.insert(entity, new WriteOptions(this.ConsistencyLevel, RetryPolicy.DEFAULT));
    }

    @Override
    public <S extends Activity> Iterable<S> save(Iterable<S> entities) {
        return null;
    }

    @Override
    public Activity findOne(ActivityPrimaryKey activityPrimaryKey) {
        return template.selectOneById(Activity.class, activityPrimaryKey);
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
