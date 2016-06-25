package sabbat.messenger.core.infrastructure.persistence;

import org.springframework.data.repository.CrudRepository;
import sabbat.messenger.core.domain.identity.aggregates.User;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Created by bruenni on 14.06.16.
 */
public interface IUserRepository extends CrudRepository<User, UUID> {
    //CompletableFuture<User> findByUserNameAsync(String userName);

    /**
     * User get by name.
     * @param userName
     * @return
     */
    User findByUserName(String userName);
}
