package sabbat.location.core.persistence.activity;

import org.springframework.data.repository.CrudRepository;
import sabbat.location.core.domain.model.Activity;

/**
 * Created by bruenni on 13.09.16.
 */
public interface IActivityRepository extends CrudRepository<Activity, ActivityPrimaryKey> {
}
