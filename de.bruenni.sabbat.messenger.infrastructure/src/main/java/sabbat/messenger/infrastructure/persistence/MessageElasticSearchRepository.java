package sabbat.messenger.infrastructure.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import sabbat.messenger.core.domain.aggregates.Message;

import java.io.Serializable;
import java.util.concurrent.Future;

/**
 * Created by bruenni on 21.06.16.
 */
public class MessageElasticSearchRepository implements org.springframework.data.repository.el{

}
