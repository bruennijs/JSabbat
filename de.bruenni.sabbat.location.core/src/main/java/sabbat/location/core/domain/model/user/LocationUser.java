package sabbat.location.core.domain.model.user;

import account.User;
import infrastructure.util.IterableUtils;
import infrastructure.util.StreamUtils;

import java.util.Map;

/**
 * Created by bruenni on 22.06.17.
 * this is a user specific to bound context of de.bruenni.sabbat.location
 * containing its specific parameters.
 */
public class LocationUser {
	public static final String ACTIVITY_NOTIFICATION_ENABLED_PROPERTY = "ActivityNotificationEnabled";
	private final Boolean notificationEnabled;
	private User user;

	public LocationUser(User genericDomainUser) {
		user = genericDomainUser;

		Map<String, Object> propertyMap = StreamUtils.toMap(IterableUtils.stream(user.getProperties()));

		this.notificationEnabled = parseNotificationEnabled(propertyMap);
	}

	/**
	 * Parses property ACTIVITY_NOTIFICATION_ENABLED_PROPERTY
	 * @param propertyMap
	 * @param <TValue>
	 * @return
	 */
	private <TValue> TValue parseNotificationEnabled(Map<String, Object> propertyMap) {
		if (propertyMap.containsKey(ACTIVITY_NOTIFICATION_ENABLED_PROPERTY))
			return  (TValue)propertyMap.get(ACTIVITY_NOTIFICATION_ENABLED_PROPERTY);

		return null;
	}

	/**
	 * Gets value indicating whether users wants to be notified about group activity in gerneral.
	 * Can be null.
	 * @return
	 */
	public Boolean getNotificationEnabled() {
		return notificationEnabled;
	}

	public static LocationUser from(User user) {
		return new LocationUser(user);
	}
}
