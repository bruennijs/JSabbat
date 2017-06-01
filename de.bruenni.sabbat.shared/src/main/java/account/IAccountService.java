package account;

import identity.GroupRef;
import identity.UserRef;

/**
 * Created by bruenni on 15.10.16.
 */
public interface IAccountService {

    /**
     * gets user by primary id.
     * @param userId
     * @return null if not existing.
     */
    User getUserById(String userId);

    /**
     * gets user by reference.
     * @param uderRef
     * @return
     */
    User getUserByRef(UserRef uderRef);

    /**
     * Gets all users by group name.
     * @param group group reference to get users for
     * @return
     */
    Iterable<User> getUsersByGroup(GroupRef group);
}
