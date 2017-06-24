package notification;

import account.User;

/**
 * Created by bruenni on 22.06.17.
 */
public interface UserNotificationService {
	/**
	 * Sends a notification message to the specified user
	 */
	rx.Observable<NotificationMessage<? extends NotificationContent>> notify(NotificationMessage<? extends NotificationContent> message);
}
