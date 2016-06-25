package sabbat.messenger.core.infrastructure.persistence;

import sabbat.messenger.core.domain.messenger.aggregates.IMessage;
import org.springframework.data.repository.CrudRepository;
import sabbat.messenger.core.domain.messenger.aggregates.Message;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Created by bruenni on 14.06.16.
 */
public interface IMessageRepository extends CrudRepository<Message, UUID> {
    /**
     * Finds message by sender.
     */
    CompletableFuture<Message> findBySenderId(UUID id);
}
