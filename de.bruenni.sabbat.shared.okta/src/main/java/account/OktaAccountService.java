package account;

import com.okta.sdk.client.Client;
import com.okta.sdk.resource.group.*;
import com.okta.sdk.resource.user.UserList;
import com.okta.sdk.resource.user.UserProfile;
import identity.GroupRef;
import identity.UserRef;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by bruenni on 15.06.17.
 */
public class OktaAccountService implements IAccountService {

	private Client client;

	public OktaAccountService(Client client) {
		this.client = client;
	}

	/**
	 * Gets a user by id.
	 * @param userId
	 * @return user instance.
	 */
	public User getUserById(String userId) {
		//com.okta.sdk.resource.user.User oktaUser = StreamSupport.stream(client.listUsers().spliterator(), false).filter(u -> u.getId().equals(userId)).findFirst().get();

		com.okta.sdk.resource.user.User oktaUser = client.getUser(userId);

		GroupList groups = oktaUser.listGroups();

		return toAggregate(oktaUser, groups);
	}

	/**
	 * Gets a user by userRef instance.
	 * @param uderRef
	 * @return
	 */
	public User getUserByRef(UserRef uderRef) {
		return null;
	}

	/**
	 * Gets all users of a group.
	 * @param group group reference to get users for
	 * @return list of users of that group.
	 */
	public Iterable<User> getUsersByGroup(GroupRef group) {
		return null;
	}

	private User toAggregate(com.okta.sdk.resource.user.User oktaUser, GroupList groups) {

		Stream<com.okta.sdk.resource.group.Group> groupStream = StreamSupport.stream(groups.spliterator(), false);

		UserProfile userProfile = oktaUser.getProfile();
		return new User(oktaUser.getId(), userProfile.getLogin(), userProfile.getEmail(), groupStream.map(g -> toAggregate(g)).collect(Collectors.toList()));
	}

	private GroupRef toAggregate(com.okta.sdk.resource.group.Group group) {
		return new GroupRef(group.getId());
	}
}
