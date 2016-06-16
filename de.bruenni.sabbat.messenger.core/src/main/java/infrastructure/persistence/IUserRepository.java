package infrastructure.persistence;

import org.springframework.data.repository.CrudRepository;
import sabbat.messenger.core.domain.aggregates.identity.User;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Created by bruenni on 14.06.16.
 */
public interface IUserRepository extends CrudRepository<User, UUID> {
    /**
     * Finds user by user name
     * @param userName
     * @return
     */
    CompletableFuture<User> findByUserNameAsync(String userName);

    /**
     * User get by name.
     * @param userName
     * @return
     */
    User findByUserName(String userName);
}
