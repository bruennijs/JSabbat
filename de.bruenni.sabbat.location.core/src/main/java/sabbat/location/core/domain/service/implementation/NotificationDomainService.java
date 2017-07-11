package sabbat.location.core.domain.service.implementation;

import account.IAccountService;
import account.User;
import notification.NotificationContent;
import notification.NotificationMessage;
import notification.UserNotificationService;
import notification.TextNotificationContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import sabbat.location.core.domain.events.activity.ActivityRelationCreatedEvent;
import sabbat.location.core.domain.events.activity.ActivityStoppedEvent;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.user.LocationUser;
import sabbat.location.core.persistence.activity.IActivityRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by bruenni on 22.06.17.
 */
public class NotificationDomainService {

    private static Logger Log = LoggerFactory.getLogger(NotificationDomainService.class);

	private IAccountService accountService;
	private IActivityRepository activityRepository;
	private UserNotificationService notificationService;

	public NotificationDomainService(IAccountService accountService, IActivityRepository activityRepository, UserNotificationService notificationService) {
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

	@EventListener
	public void onActivityStopped(ActivityStoppedEvent stopEvent)
	{
        User usersOfStoppedActivity = this.accountService.getUserById(stopEvent.getAggregate().getUserId());

        // find all related activities and notify the owning users about the stop
        List<User> usersOfRelatedActivities = stopEvent.getAggregate().getRelatedActivities().stream().map(related -> this.accountService.getUserById(related.getUserId())).collect(Collectors.toList());

        usersOfRelatedActivities.forEach(userOfRelatedActivity ->
        {
            LocationUser locationUser = LocationUser.from(userOfRelatedActivity);
            if (locationUser.getNotificationEnabled())
            {
                //Log.debug(String.format("onActivityStopped: notify message! [%1%]", message));

                notificationService.notify(buildNotificationMessageOnStop(userOfRelatedActivity, usersOfStoppedActivity, stopEvent.getAggregate()));
            }
        });
    }

    private NotificationMessage<? extends NotificationContent> buildNotificationMessageOnStop(User userToSendTo, User userOfStoppedActivity, Activity activity) {

        String contextText = String.format("The user [%1s] stopped its active activity [%2s] at [%3s]. Take a look at the activty stats!",
                userOfStoppedActivity.getName(),
                activity.getTitle(),
                activity.getStarted().toString());

        return new NotificationMessage<TextNotificationContent>(userToSendTo,
                "Sabbat location: User is active!",
                new TextNotificationContent(contextText));
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

	private LocationUser getLocationUserById(String userId)
    {
        User user1 = accountService.getUserById(userId);
        return LocationUser.from(user1);
    }
}
