package notification.implementation;

import account.User;
import notification.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

/**
 * Created by bruenni on 22.06.17.
 */
public class EmailNotificationService implements NotificationService {

	private static Logger Log = LoggerFactory.getLogger(EmailNotificationService.class);

	@Override
	public Observable notify(User user, String message) {
		Log.info("[%1s] -> [%2s]", user.toString(), message);
		return Observable.empty();
	}
}
