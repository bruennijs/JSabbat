package sabbat.location.infrastructure.integrationtest.persistence.activity;

import com.google.common.collect.Lists;
import infrastructure.common.event.Event;
import infrastructure.util.IterableUtils;
import infrastructure.util.Tuple2;
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
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StreamUtils;
import sabbat.location.core.builder.ActivityBuilder;
import sabbat.location.core.domain.events.activity.ActivityRelationCreatedEvent;
import sabbat.location.core.domain.events.activity.ActivityStartedEvent;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.events.activity.ActivityEvent;
import sabbat.location.core.domain.model.ActivityRelation;
import sabbat.location.core.persistence.activity.IActivityRepository;
import sabbat.location.infrastructure.integrationtest.IntegrationTestConfig;
import sabbat.location.infrastructure.persistence.TransactionScope;
import test.matcher.LambdaMatcher;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by bruenni on 17.03.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = { "test" })
@SpringApplicationConfiguration(classes = { IntegrationTestConfig.class })
public class JpaActivityRepositoryTest {

	@Autowired
	@Qualifier("activityRepository")
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

	private IActivityRepository getRepo() {
		return ctx.getBean("activityRepository", IActivityRepository.class);
	}
}
