package notification;

import account.User;

/**
 * Created by bruenni on 22.06.17.
 */
public interface NotificationService {
	/**
	 * Sends a notification message to the specified user
	 */
	rx.Observable notify(User user, String message);
}
