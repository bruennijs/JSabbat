package account.implementation;

import account.IAccountService;
import account.User;
import identity.GroupRef;
import identity.UserRef;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * Created by bruenni on 16.03.17.
 */
public class StormpathAccountService implements IAccountService {
	@Cacheable()
	@Override
	public User getUserById(String userId) {
		return null;
	}

	@Override
	public User getUserByRef(UserRef userRef) {
		return null;
	}

	@Override
	public List<UserRef> getUsersByGroup(GroupRef group) {
		return null;
	}
}
