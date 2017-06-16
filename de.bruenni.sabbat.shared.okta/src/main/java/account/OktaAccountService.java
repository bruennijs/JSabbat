package account;

import com.okta.sdk.client.Client;
import com.okta.sdk.resource.group.*;
import com.okta.sdk.resource.group.Group;
import com.okta.sdk.resource.user.UserProfile;
import identity.GroupRef;
import identity.UserRef;

import java.util.List;
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

		return toUser(oktaUser, groups);
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
	public List<UserRef> getUsersByGroup(GroupRef group) {
		Group oktaGroup = client.getGroup(group.getId());
		return StreamSupport.stream(oktaGroup.listUsers().spliterator(), false).map(og -> toUserRef(og, og.listGroups())).collect(Collectors.toList());
	}

	private User toUser(com.okta.sdk.resource.user.User oktaUser, GroupList groups) {

		Stream<com.okta.sdk.resource.group.Group> groupStream = StreamSupport.stream(groups.spliterator(), false);

		UserProfile userProfile = oktaUser.getProfile();
		return new User(oktaUser.getId(), userProfile.getLogin(), userProfile.getEmail(), groupStream.map(g -> toGroupRef(g)).collect(Collectors.toList()));
	}

	private UserRef toUserRef(com.okta.sdk.resource.user.User oktaUser, GroupList groups) {
		Stream<com.okta.sdk.resource.group.Group> groupStream = StreamSupport.stream(groups.spliterator(), false);

		return new UserRef(oktaUser.getId(), oktaUser.getProfile().getLogin(), groupStream.map(g -> toGroupRef(g)).collect(Collectors.toList()));
	}

	private GroupRef toGroupRef(com.okta.sdk.resource.group.Group group) {
		return new GroupRef(group.getId());
	}
}
