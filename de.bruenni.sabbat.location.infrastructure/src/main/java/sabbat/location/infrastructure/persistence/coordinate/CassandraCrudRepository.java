package sabbat.location.infrastructure.persistence.coordinate;

import com.datastax.driver.core.QueryOptions;
import com.datastax.driver.core.querybuilder.Ordering;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.sun.javafx.binding.StringFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cassandra.core.RetryPolicy;
import org.springframework.cassandra.core.WriteOptions;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.repository.CrudRepository;
import sabbat.location.core.domain.model.coordinate.UserCoordinate;

import java.io.Serializable;

/**
 * Created by bruenni on 06.07.17.
 */
public class CassandraCrudRepository<T, ID extends Serializable> implements CrudRepository<T, ID> {

    protected Logger log = LoggerFactory.getLogger(CassandraCrudRepository.class);

    private CassandraTemplate template;
    private Class<T> runtimeClass;

    @Value("${cassandra.usercoordinate.consistencylevel}")
    public org.springframework.cassandra.core.ConsistencyLevel ConsistencyLevel;

    /**
     * Constructor
     * @param template
     */
    public CassandraCrudRepository(CassandraTemplate template, Class<T> runtimeClass) {
        this.template = template;
        this.runtimeClass = runtimeClass;
    }

    @Override
    public <S extends T> S save(S entity) {
        log.debug(StringFormatter.format("INSERT into activity [%1s, ConsistencyLevel=%2s]", entity.toString(), this.ConsistencyLevel.toString()).getValue());

        return template.insert(entity, new WriteOptions(this.ConsistencyLevel, RetryPolicy.DEFAULT));
    }

    @Override
    public <S extends T> Iterable<S> save(Iterable<S> entities) {
        return null;
    }

    @Override
    public T findOne(ID id) {
        return null;
    }

    @Override
    public boolean exists(ID id) {
        return false;
    }

    @Override
    public Iterable<T> findAll() {
        return null;
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> ids) {

        Select query = QueryBuilder
                .select()
                .from(UserCoordinate.TABLE_NAME);
                //.orderBy(new Ordering("captured", true));

        return template.queryForList(query, runtimeClass);
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(ID id) {

    }

    @Override
    public void delete(T entity) {

    }

    @Override
    public void delete(Iterable<? extends T> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
