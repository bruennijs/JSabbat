package account;

import identity.UserRef;

/**
 * Created by bruenni on 15.10.16.
 */
public interface IAccountService {
    /**
     * gets user by reference.
     * @param uderRef
     * @return
     */
    User getUserByRef(UserRef uderRef);
}
