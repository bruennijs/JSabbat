package account.implementation;

import account.IAccountService;
import account.User;
import identity.GroupRef;
import identity.UserRef;

/**
 * Created by bruenni on 16.03.17.
 */
public class DefaultAccountService implements IAccountService {
	@Override
	public User getUserById(String userId) {
		return null;
	}

	@Override
	public User getUserByRef(UserRef uderRef) {
		return null;
	}

	@Override
	public Iterable<User> getUsersByGroup(GroupRef group) {
		return null;
	}
}
