package sabbat.location.infrastructure.persistence.measurement;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import org.springframework.data.cassandra.core.CassandraTemplate;
import sabbat.location.core.domain.model.coordinate.UserCoordinate;
import sabbat.location.core.domain.model.coordinate.UserCoordinatePrimaryKey;
import sabbat.location.core.persistence.coordinate.UserCoordinateRepository;

/**
 * Created by bruenni on 06.07.17.
 */
public class CassandraUserCoordinateRepository
        extends CassandraCrudRepository<UserCoordinate, UserCoordinatePrimaryKey>
        implements UserCoordinateRepository {

    /**
     * Constructor
     *
     * @param template
     */
    public CassandraUserCoordinateRepository(CassandraTemplate template) {
        super(template, UserCoordinate.class);
    }

    @Override
    public Iterable<UserCoordinate> findActivityCoordinates(String userid, String activityId) {
        Select query = QueryBuilder
                .select()
                .from(UserCoordinate.TABLE_NAME);

        query.where(QueryBuilder.eq(UserCoordinatePrimaryKey.COLUMN_USER_ID, userid));
        query.where(QueryBuilder.eq(UserCoordinatePrimaryKey.COLUMN_ACTIVITY_ID, activityId));

        return getTemplate().select(query, UserCoordinate.class);
    }
}
