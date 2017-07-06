package sabbat.location.infrastructure.persistence.coordinate;

import org.springframework.data.cassandra.core.CassandraTemplate;
import sabbat.location.core.domain.model.coordinate.UserCoordinate;
import sabbat.location.core.domain.model.coordinate.UserCoordinatePrimaryKey;
import sabbat.location.core.persistence.coordinate.UserCoordinateRepository;

/**
 * Created by bruenni on 06.07.17.
 */
public class CassandraUserCoordinateRepository extends CassandraCrudRepository<UserCoordinate, UserCoordinatePrimaryKey> {

    /**
     * Constructor
     *
     * @param template
     */
    public CassandraUserCoordinateRepository(CassandraTemplate template) {
        super(template, UserCoordinate.class);
    }
}
