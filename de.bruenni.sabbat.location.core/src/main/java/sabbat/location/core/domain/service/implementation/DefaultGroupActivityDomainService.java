package sabbat.location.core.domain.service.implementation;

import account.IAccountService;
import account.User;
import com.hazelcast.util.IterableUtil;
import identity.UserRef;
import infrastructure.common.event.Event;
import infrastructure.util.IterableUtils;
import org.springframework.util.StreamUtils;
import sabbat.location.core.domain.events.activity.ActivityEvent;
import sabbat.location.core.domain.events.activity.ActivityStartedEvent;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.service.GroupActivityDomainService;
import sabbat.location.core.persistence.activity.IActivityRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
	public List<? extends Event> onActivityStarted(ActivityStartedEvent event)
	{
		// 0.5 get groups the user of started activity is in
		User startedActivityUser = accountService.getUserById(event.getAggregate().getUserId());

		// 1. find all users of all groups the user of the started activity is in
		//Arrays.stream(startedActivityUser.getGroups()).map()
		//accountService.getUsersByGroup()
		List<UserRef> groupsUsers = startedActivityUser
			.getGroupRefs()
			.stream()
			.flatMap(gr -> accountService.getUsersByGroup(gr).stream())    // gets all users per group
			.collect(Collectors.toList());

		// 2. find still active activities of the users from 1)
		Stream<String> groupsUsersIds = StreamSupport.stream(groupsUsers.spliterator(), false)
			.map(user -> user.getId());

		Iterable<Activity> groupsUsersActiveActivities = activityRepository.findActiveActivitiesByUserIds(groupsUsersIds.collect(Collectors.toList()));

		// 3. relate associated activities with each other
		Stream<ActivityEvent> domainEventStream = IterableUtils.stream(groupsUsersActiveActivities)
			.flatMap(activity -> event.getAggregate().relateActivity(activity).stream());

		return domainEventStream.collect(Collectors.toList());
	}
}
