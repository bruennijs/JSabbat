package sabbat.location.core.domain.service.implementation;

import account.IAccountService;
import account.User;
import infrastructure.common.event.Event;
import sabbat.location.core.domain.events.activity.ActivityStartedEvent;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.service.GroupActivityDomainService;
import sabbat.location.core.persistence.activity.IActivityRepository;

import java.util.Arrays;
import java.util.List;

/**
 * Created by bruenni on 16.03.17.
 */
public class DefaultGroupActivityDomainService implements GroupActivityDomainService {

	private IActivityRepository activityRepository;
	private IAccountService accountService;

	/**
	 * Constructor.
	 * @param activityRepository
	 * @param accountService
	 */
	public DefaultGroupActivityDomainService(IActivityRepository activityRepository, IAccountService accountService) {
		this.activityRepository = activityRepository;
		this.accountService = accountService;
	}

	/**
	 * 0. Find user of who started activity
	 * 1. Find all users of the group the user is in who started the activity
	 * 2.
	 * @param event
	 * @return list of domain events created by relating activities.
	 */
	@Override
	public List<Event> onActivityStarted(ActivityStartedEvent event)
	{
		// 0.5 get groups the user of started activity is in
		User startedActivityUser = accountService.getUserById(event.getAggregate().getUserId());

		// 1. find all users of all groups the user of the started activity is in
		//Arrays.stream(startedActivityUser.getGroups()).map()
		//accountService.getUsersByGroup()


		// 2. find still active activities of the users from 1)
		// activityRepository.findByUserIds();


			// 3. relate associated activities with each other
		//startedActivity.relateActivity(null);
		return Arrays.asList();
	}
}
