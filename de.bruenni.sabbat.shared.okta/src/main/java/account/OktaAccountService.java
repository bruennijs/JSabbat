package account;

import com.okta.sdk.client.Client;
import com.okta.sdk.resource.group.*;
import com.okta.sdk.resource.group.Group;
import com.okta.sdk.resource.user.UserProfile;
import identity.GroupRef;
import identity.UserRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by bruenni on 15.06.17.
 */
public class OktaAccountService implements IAccountService {

	private static Logger log = LoggerFactory.getLogger(OktaAccountService.class);

	private Client client;

	public OktaAccountService(Client client) {
		this.client = client;
	}

	/**
	 * Gets a user by id.
	 * @param userId
	 * @return user instance.
	 */
	@Cacheable(cacheNames = "users", cacheManager = "cacheManager", key = "#userId")
	public User getUserById(String userId) {
		//com.okta.sdk.resource.user.User oktaUser = StreamSupport.stream(client.listUsers().spliterator(), false).filter(u -> u.getId().equals(userId)).findFirst().get();

		com.okta.sdk.resource.user.User oktaUser = client.getUser(userId);

		OktaUserConverter<User> converter = new OktaUserConverter<>((u, g) ->
		{
			UserProfile userProfile = oktaUser.getProfile();
			//Object content = userProfile.getOrDefault("ActivityNotificationEnabled", null);
			return new User(u.getId(), userProfile.getLogin(), userProfile.getEmail(), g, userProfile);
		});


		return converter.convert(oktaUser);
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
	@Cacheable(cacheNames = "groups",
		key = "#group.id",
		cacheManager = "cacheManager")
	public List<UserRef> getUsersByGroup(GroupRef group) {
		Group oktaGroup = client.getGroup(group.getId());

		//log.debug("GROUP: " + oktaGroup.getId());
		OktaUserConverter<UserRef> converter = new OktaUserConverter<>((u, g) ->
		{
			UserProfile userProfile = u.getProfile();
			return new UserRef(u.getId(), userProfile.getLogin(), g);
		});


		return StreamSupport.stream(oktaGroup.listUsers().spliterator(), false).map(oktaUser -> converter.convert(oktaUser)).collect(Collectors.toList());
	}
}
