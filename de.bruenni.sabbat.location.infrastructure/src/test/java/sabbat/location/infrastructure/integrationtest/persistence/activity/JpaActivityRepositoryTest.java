package sabbat.location.infrastructure.integrationtest.persistence.activity;

import com.google.common.collect.Lists;
import infrastructure.common.event.Event;
import infrastructure.util.IterableUtils;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Equals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sabbat.location.core.builder.ActivityBuilder;
import sabbat.location.core.domain.events.activity.ActivityEvent;
import sabbat.location.core.domain.events.activity.ActivityRelationCreatedEvent;
import sabbat.location.core.domain.events.activity.ActivityStartedEvent;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.ActivityRelation;
import sabbat.location.core.persistence.activity.IActivityRepository;
import sabbat.location.infrastructure.integrationtest.IntegrationTestConfig;
import test.matcher.LambdaMatcher;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by bruenni on 17.03.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = { "dev" })
@SpringBootTest(classes = { IntegrationTestConfig.class })
public class JpaActivityRepositoryTest {

	public static final String JPA_ACTIVITY_REPOSITORY_QUALIFIER = "jpaActivityRepository";

	@Autowired
	@Qualifier(JPA_ACTIVITY_REPOSITORY_QUALIFIER)
	public IActivityRepository activityRepository;

	@Autowired
	public ApplicationContext ctx;
	private boolean truncated = false;

	@org.junit.Before
	public void setup() {

		//truncateDb();
	}

	private void truncateDb() {
		if (!truncated) {
			activityRepository.truncate();
			truncated = true;
		}
	}

	@Test
	public void store_and_query_activity_by_id() {
		Activity activity = new ActivityBuilder().build();
		Activity saved = activityRepository.save(activity);

		Assert.assertThat(saved.getId(), IsNot.not(0l));

		Activity readEntity = activityRepository.findOne(saved.getId());

		Assert.assertThat(readEntity, new Equals(saved));
	}

	@Test
	public void when_relate_two_activities_via_activitys_relation_expect_activity_loaded_contains_relation() {
		Activity activity1 = new ActivityBuilder().build();
		Activity activity2 = new ActivityBuilder().build();

		activity1 = activityRepository.save(activity1);
		activity2 = activityRepository.save(activity2);

		activity1.relateActivity(activity2);
		activity1 = activityRepository.save(activity1);
		activity2 = activityRepository.save(activity2);
		//this.activityRepository.refresh(activity2);

		IActivityRepository activityRepositoryTmp = getRepo();

		activity1 = activityRepositoryTmp.findOne(activity1.getId());
		activity2 = activityRepositoryTmp.findOne(activity2.getId());


		Assert.assertThat(activity1.getRelations().size(), IsEqual.equalTo(1));
		Assert.assertThat(activity2.getRelations().size(), IsEqual.equalTo(1));
		//Assert.assertThat(activity2Tmp.getRelations().size(), IsEqual.equalTo(1));

		final Activity a1Tmp = activity1;
		final Activity a2Tmp = activity2;


		Assert.assertThat(activity1.getRelatedActivities(),
			Matchers.contains(IsEqual.equalTo(activity2)));

		Assert.assertThat(activity2.getRelatedActivities(),
			Matchers.contains(IsEqual.equalTo(activity1)));
	}

	public void when_relate_two_activities_via_activitys_relation_list_expect_both_activities_contains_activityrelation_referencing_both_activities() {
		Activity activity1 = new ActivityBuilder().build();
		Activity activity2 = new ActivityBuilder().build();

		Activity mergedActivity1 = activityRepository.save(activity1);
		Activity mergedActivity2 = activityRepository.save(activity2);

		activityRepository.save(new ActivityRelation(mergedActivity1, mergedActivity2));

		Activity readActivity1 = activityRepository.findOne(mergedActivity2.getId());
		Activity readActivity2 = activityRepository.findOne(mergedActivity2.getId());

		Assert.assertThat(readActivity1.getRelations().size(), IsEqual.equalTo(1));

		Assert.assertThat(readActivity1.getRelations(),
			IsIterableContainingInAnyOrder.containsInAnyOrder(Lists.newArrayList(new LambdaMatcher<>(r -> r.getRelatedActivities().contains(mergedActivity1), "cannot find activity 1"))));

		Assert.assertThat(readActivity1.getRelations(), IsIterableContainingInAnyOrder.containsInAnyOrder(Lists.newArrayList(new LambdaMatcher<>(r ->
			r.getRelatedActivities().contains(mergedActivity2), "cannot find activity 2"))));

		Assert.assertThat(readActivity2.getRelations().size(), IsEqual.equalTo(1));

		Assert.assertThat(readActivity2.getRelations(), IsIterableContainingInAnyOrder.containsInAnyOrder(Lists.newArrayList(new LambdaMatcher<>(r ->
			r.getRelatedActivities().contains(mergedActivity1), "cannot find activity 1"))));

		Assert.assertThat(readActivity2.getRelations(), IsIterableContainingInAnyOrder.containsInAnyOrder(Lists.newArrayList(new LambdaMatcher<>(r ->
			r.getRelatedActivities().contains(mergedActivity2), "cannot find activity 2"))));
	}

	@Test
	public void when_start_activity_expect_domain_event_persisted() throws Exception {
		Activity activity1 = new ActivityBuilder().build();
		final ActivityEvent startedEvent = activity1.start();

		activity1 = activityRepository.save(activity1);

		IActivityRepository activityRepositoryTmp = getRepo();

		// find in new first level cache to generate new SQL query
		Activity activityRead = activityRepositoryTmp.findOne(activity1.getId());

		Assert.assertThat(activityRead.getEvents(),
			Matchers.contains(
				Matchers.allOf(new LambdaMatcher<Event>(event -> event instanceof ActivityStartedEvent, "Event is instance of ActivityStartedEvent"))));
	}

	@Test
	public void when_relate_activity_expect_ActivityRelationCreatedEvent_with_related_activity_persisted() throws Exception {

		Activity activity1 = new ActivityBuilder().build();
		Activity activity2 = new ActivityBuilder().build();

		activity1 = activityRepository.save(activity1);
		activity2 = activityRepository.save(activity2);

		activity1.relateActivity(activity2);

		activity1 = activityRepository.save(activity1);

		// build new first level cache
		IActivityRepository activityRepositoryTmp = getRepo();
		activity1 = activityRepositoryTmp.findOne(activity1.getId());

		List<ActivityRelationCreatedEvent> activityRelationCreatedEvents = IterableUtils.stream(activity1.getEvents()).filter(e -> e instanceof ActivityRelationCreatedEvent).map(e -> (ActivityRelationCreatedEvent) e).collect(Collectors.toList());

		final Activity a2Tmp = activity2;

		Assert.assertThat(activityRelationCreatedEvents, Matchers.contains(
			LambdaMatcher.<ActivityRelationCreatedEvent>isMatching(e -> e.getAttributes().getRelatedActivityId().equals(a2Tmp.getId()), "event relates to activity2 id")));
	}

	@Test
	public void when_find_by_userd_id_expect_all_activities_of_multiple_users_are_returned() throws Exception {
		String userId1 = UUID.randomUUID().toString();
		String userId2 = UUID.randomUUID().toString();
		String useridNotPartOfSet = UUID.randomUUID().toString();

		Activity activity1 = new ActivityBuilder().withUserId(userId1).build();
		Activity activity2 = new ActivityBuilder().withUserId(userId2).build();
		Activity activityNotIn1 = new ActivityBuilder().withUserId(useridNotPartOfSet).build();

		activity1 = activityRepository.save(activity1);
		activity2 = activityRepository.save(activity2);
		activityNotIn1 = activityRepository.save(activityNotIn1);

		Iterable<Activity> activities = activityRepository.findByUserIds(Arrays.asList(userId1, userId2));

		Assert.assertThat(activities, Matchers.contains(IsEqual.equalTo(activity1), IsEqual.equalTo(activity2)));
		//Assert.assertThat(activities, Matchers.hasItem(IsEqual.equalTo(activity1)));
		//Assert.assertThat(activities, Matchers.hasItem(IsEqual.equalTo(activity2)));
	}

	@Test
	public void when_find_active_activities_by_user_id_list_in_set_of_started_activties_not_in_parameter_set() throws Exception {
		Activity activityInAndStarted = new ActivityBuilder().build();
		Activity activityInAndNotStarted = new ActivityBuilder().withUserId(activityInAndStarted.getUserId()).build();
		Activity activityNotInAndStarted = new ActivityBuilder().build();

		activityInAndStarted.start();
		activityNotInAndStarted.start();

		activityInAndStarted = activityRepository.save(activityInAndStarted);
		activityInAndNotStarted = activityRepository.save(activityInAndNotStarted);
		activityNotInAndStarted = activityRepository.save(activityNotInAndStarted);

		Iterable<Activity> activities = activityRepository.findActiveActivitiesByUserIds(Arrays.asList(activityInAndStarted.getUserId()));

		Assert.assertThat(activities, Matchers.contains(IsEqual.equalTo(activityInAndStarted)));
	}

	@Test
	public void when_find_active_activities_by_user_id_list_in_set_of_started_and_stopped_activities_in_set_of_parameter_list() throws Exception {
		Activity activityInAndStarted = new ActivityBuilder().withTitle("startedAndNotStoppedYet").build();
		Activity activityInAndStopped = new ActivityBuilder()
			.withUserId(activityInAndStarted.getUserId())
			.withTitle("stoppedactivity")
			.build();

		activityInAndStarted.start();
		activityInAndStopped.start();
		activityInAndStopped.stop(activityInAndStarted.getStarted().toInstant().plus(Duration.ofMinutes(5)));

		activityInAndStarted = activityRepository.save(activityInAndStarted);
		activityRepository.save(activityInAndStopped);

		Iterable<Activity> activities = activityRepository.findActiveActivitiesByUserIds(Arrays.asList(activityInAndStarted.getUserId()));

		Assert.assertThat(activities, Matchers.contains(IsEqual.equalTo(activityInAndStarted)));
	}

	@Test
	public void when_find_active_activities_by_user_id_list_where_activity_was_started_and_stopped_multiple_times() throws Exception {
		Activity activityInAndStarted = new ActivityBuilder().withTitle("startedAndStoppedMultipleTimesNotStoppedYet").build();

		Instant now = Instant.now(Clock.systemUTC());

		activityInAndStarted.start();
		activityInAndStarted.stop(now.plus(Duration.ofMinutes(1)));
		activityInAndStarted.start(now.plus(Duration.ofMinutes(2)));

		activityInAndStarted = activityRepository.save(activityInAndStarted);

		Iterable<Activity> activities = activityRepository.findActiveActivitiesByUserIds(Arrays.asList(activityInAndStarted.getUserId()));

		Assert.assertThat(activities, Matchers.contains(IsEqual.equalTo(activityInAndStarted)));
	}

	private IActivityRepository getRepo() {
		return ctx.getBean(JPA_ACTIVITY_REPOSITORY_QUALIFIER, IActivityRepository.class);
	}
}
