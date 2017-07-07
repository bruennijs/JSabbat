package sabbat.location.infrastructure.persistence.measurement;

import com.datastax.driver.core.querybuilder.Batch;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.sun.javafx.binding.StringFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cassandra.core.ConsistencyLevel;
import org.springframework.cassandra.core.RetryPolicy;
import org.springframework.cassandra.core.WriteOptions;
import org.springframework.data.cassandra.convert.CassandraConverter;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.mapping.CassandraPersistentEntity;
import org.springframework.data.repository.CrudRepository;
import sabbat.location.core.domain.model.coordinate.UserCoordinate;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by bruenni on 06.07.17.
 */
public abstract class CassandraCrudRepository<T, ID extends Serializable> implements CrudRepository<T, ID> {

    protected Logger log = LoggerFactory.getLogger(CassandraCrudRepository.class);

    private CassandraTemplate template;
    private Class<T> runtimeClass;

    @Value("${cassandra.usercoordinate.consistencylevel}")
    public com.datastax.driver.core.ConsistencyLevel ConsistencyLevel;

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

        return template.insert(entity, new WriteOptions(org.springframework.cassandra.core.ConsistencyLevel.ONE, RetryPolicy.DEFAULT));
    }

    @Override
    public <S extends T> Iterable<S> save(Iterable<S> entities) {
        log.debug(StringFormatter.format("INSERT into activity [ConsistencyLevel=%1s]", this.ConsistencyLevel.toString()).getValue());

        Batch batch = QueryBuilder.batch();
        for (S entity :
                entities) {
            batch.add(createFullInsert(entity));
        }

        template.execute(batch);

        return entities;
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

/*        Select query = QueryBuilder
                .select()
                .from(UserCoordinate.TABLE_NAME);
                //.orderBy(new Ordering("captured", true));*/

        return template.selectBySimpleIds(this.runtimeClass, ids);
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

    protected CassandraTemplate getTemplate() {
        return template;
    }


    private <S extends T> Insert createFullInsert(S entity) {

        CassandraConverter converter = getTemplate().getConverter();
        CassandraPersistentEntity<?> persistentEntity = converter.getMappingContext()
                .getPersistentEntity(entity.getClass());
        Map<String, Object> toInsert = new LinkedHashMap<String, Object>();

        converter.write(entity, toInsert, persistentEntity);

        Insert insert = QueryBuilder.insertInto(persistentEntity.getTableName().toCql());
        insert.setConsistencyLevel(this.ConsistencyLevel);

        for (Map.Entry<String, Object> entry : toInsert.entrySet()) {
            insert.value(entry.getKey(), entry.getValue());
        }

        return insert;
    }
}
