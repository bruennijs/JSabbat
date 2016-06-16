package infrastructure.persistence;

import sabbat.messenger.core.domain.aggregates.Message;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by bruenni on 14.06.16.
 */
interface IMessageRepository extends CrudRepository<Message, Long> {

}
