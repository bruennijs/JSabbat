package sabbat.location.infrastructure.persistence.activity;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.sun.javafx.binding.StringFormatter;
import org.springframework.cassandra.core.RetryPolicy;
import org.springframework.cassandra.core.WriteOptions;
import org.springframework.data.cassandra.core.CassandraTemplate;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.ActivityCoordinate;

import java.util.List;

/**
 * Created by bruenni on 21.09.16.
 */
public class CassandraActivityRepository extends CassandraActivityBaseRepository<Activity> {

    /**
     * Constructor
     *
     * @param template
     */
    public CassandraActivityRepository(CassandraTemplate template) {
        super(template);

        /*findActivityCoordinatesQueryOptions = new org.springframework.cassandra.core.QueryOptions();
        findActivityCoordinatesQueryOptions.setConsistencyLevel(getConsistencyLevel());*/
    }

    @Override
    public Iterable<ActivityCoordinate> insertCoordinate(List<ActivityCoordinate> coordinates) {

        log.debug(StringFormatter.format("INSERT into activity_coordinates [count=%1d, ConsistencyLevel=%2s]",
                coordinates.size(),
                getConsistencyLevel().toString()).getValue());

/*        Insert insert = QueryBuilder.insertInto(ActivityCoordinate.ACTIVITY_COORDINATES_TABLE_NAME);
        insert.setConsistencyLevel(convertFromSpringConsistencyLevel());
        insert.*/

        return getTemplate().insert(coordinates, new WriteOptions(getConsistencyLevel(), RetryPolicy.DEFAULT));
    }

    @Override
    public Iterable<ActivityCoordinate> findActivityCoordinates(Activity aggregateRoot) {

        Select select = QueryBuilder.select()
                .from("activity_coordinates");

        select.where(QueryBuilder.eq("userid", aggregateRoot.getKey().getUserId()));
        select.where(QueryBuilder.eq("activityid", aggregateRoot.getKey().getId()));
        select.setConsistencyLevel(convertFromSpringConsistencyLevel());

        //select.setFetchSize(100);
        //select.setPagingState(PagingState.fromString());

        log.debug(StringFormatter.format("select from activity_coordinates [statement=%1s]", select.getQueryString()).getValue());

        return getTemplate().select(select.getQueryString(), ActivityCoordinate.class);
    }

    private com.datastax.driver.core.ConsistencyLevel convertFromSpringConsistencyLevel() {
        return com.datastax.driver.core.ConsistencyLevel.valueOf(getConsistencyLevel().name());
    }
}
