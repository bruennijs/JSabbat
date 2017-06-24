package sabbat.location.core.domain.service.implementation;

import account.IAccountService;
import account.User;
import notification.NotificationContent;
import notification.NotificationMessage;
import notification.NotificationService;
import notification.TextNotificationContent;
import org.springframework.context.event.EventListener;
import sabbat.location.core.domain.events.activity.ActivityRelationCreatedEvent;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.user.LocationUser;
import sabbat.location.core.persistence.activity.IActivityRepository;

/**
 * Created by bruenni on 22.06.17.
 */
public class NotificationDomainService {

	private IAccountService accountService;
	private IActivityRepository activityRepository;
	private NotificationService notificationService;

	public NotificationDomainService(IAccountService accountService, IActivityRepository activityRepository, NotificationService notificationService) {
		this.accountService = accountService;
		this.activityRepository = activityRepository;
		this.notificationService = notificationService;
	}

	@EventListener
	public void onActivityRelated(ActivityRelationCreatedEvent event)
	{
		// 1. check whether user wants to be informed about activities active simultanously
		User user1 = accountService.getUserById(event.getAggregate().getUserId());
		LocationUser locationUser1 = LocationUser.from(user1);

		// 2. find related activity
		Activity relatedActivity = activityRepository.findOne(event.getAttributes().getRelatedActivityId());

		User user2 = accountService.getUserById(relatedActivity.getUserId());
		LocationUser locationUser2 = LocationUser.from(user2);

		// 2. notify user if enabled
		if (locationUser1.getNotificationEnabled())
		{
			notificationService.notify(buildNotificationMessage(user1, user2, relatedActivity));
		}

		if (locationUser2.getNotificationEnabled())
		{
			notificationService.notify(buildNotificationMessage(user2, user1, event.getAggregate()));
		}
	}

	private NotificationMessage<? extends NotificationContent> buildNotificationMessage(User userToSendTo, User userAlsoActive, Activity activity) {

		String contextText = String.format("The user [%1s] is also active currently and started an activity [%2s] at [%3s].",
			userAlsoActive.getName(),
			activity.getTitle(),
			activity.getStarted().toString());

		return new NotificationMessage<TextNotificationContent>(userToSendTo,
			"Sabbat location: User is active!",
			new TextNotificationContent(contextText));
	}
}
