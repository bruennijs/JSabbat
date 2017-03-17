package sabbat.location.core.domain.service;

import account.IAccountService;
import account.User;
import infrastructure.common.event.IEvent;
import sabbat.location.core.domain.events.ActivityStartedEvent;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.persistence.activity.IActivityRepository;

import java.util.Arrays;

/**
 * Created by bruenni on 16.03.17.
 */
public class DefaultGroupActivityDomainService {

	private IActivityRepository activityRepository;
	private IAccountService accountService;

	public DefaultGroupActivityDomainService(IActivityRepository activityRepository, IAccountService accountService) {
		this.activityRepository = activityRepository;
		this.accountService = accountService;
	}

	public IEvent[] onNewActivityStarted(ActivityStartedEvent iEvent)
	{
		// 0. find started activity
		Activity startedActivity = activityRepository.findOne(null);

		// 0.5 get groups the user of started activity is in
		User startedActivityUser = accountService.getUserById(startedActivity.getUserId());

		// 1. find all users of all groups the user of the started activity is in
		//Arrays.stream(startedActivityUser.getGroups()).map()
		//accountService.getUsersByGroup()


		// 2. find still active activities of the users from 1)
		// activityRepository.findByUserIds();


			// 3. relate associated activities with each other
		//startedActivity.relateActivity(null);
		return new IEvent[0];
	}
}
