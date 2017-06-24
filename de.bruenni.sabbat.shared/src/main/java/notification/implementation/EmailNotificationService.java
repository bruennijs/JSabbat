package notification.implementation;

import account.IAccountService;
import account.User;
import infrastructure.util.Tuple2;
import notification.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.subjects.AsyncSubject;

import java.util.Arrays;
import java.util.concurrent.Executor;

/**
 * Created by bruenni on 22.06.17.
 */
public class EmailNotificationService implements NotificationService {

	private static Logger Log = LoggerFactory.getLogger(EmailNotificationService.class);
	private Executor executor;
	private EmailSender sender;

	/**
	 * Constructor
	 * @param executor
	 * @param sender
	 */
	public EmailNotificationService(Executor executor, EmailSender sender) {
		this.executor = executor;
		this.sender = sender;
	}

	@Override
	public Observable<NotificationMessage<? extends NotificationContent>> notify(NotificationMessage<? extends NotificationContent> message) {

		AsyncSubject<NotificationMessage<? extends NotificationContent>> returnSubject = AsyncSubject.create();

		executor.execute(() ->
		{
			//// get user for email
			//User userTo = accountService.getUserByRef(message.getTo());

			// sendText email
			try {
				sender.sendText(Arrays.asList(message.getTo().getEmail()), message.getTopic(), message.getContent().asText());
				returnSubject.onNext(message);
			} catch (Exception e) {
				returnSubject.onError(e);
			}
			finally {
				returnSubject.onCompleted();
			}
		});

		return returnSubject;
	}

	private AsyncSubject<Tuple2<User, String>> pipeline() {
/*		return AsyncSubject.create()
            .buffer(5, TimeUnit.SECONDS, 2)
            .observeOn(new ExecutorScheduler(notificationServiceThreadPoolTaskExecutor()))
            .doOnEach(tuple -> Log.info("[%1s] -> [%2s]", tuple.getT1(), tuple.getT2()));*/
		return null;
	}
}
