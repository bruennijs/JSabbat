package sabbat.location.infrastructure.integrationtest.persistence.activity;

import infrastructure.common.event.IEvent;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.collection.IsIn;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.internal.matchers.Equals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sabbat.location.core.builder.ActivityBuilder;
import sabbat.location.core.domain.events.ActivityRelationCreatedEvent;
import sabbat.location.core.domain.events.ActivityStartedEvent;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.ActivityRelation;
import sabbat.location.core.persistence.activity.IActivityRepository;
import sabbat.location.infrastructure.integrationtest.IntegrationTestConfig;
import sabbat.location.infrastructure.persistence.activity.JpaActivityRepository;
import test.matcher.LambdaMatcher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.UUID;

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

	@org.junit.Before
	public void setup()
	{
	}

	@Test
	public void store_and_query_activity_by_id()
	{
		Activity activity = new ActivityBuilder().build();
		Activity saved = activityRepository.save(activity);

		Assert.assertThat(saved.getId(), IsNot.not(0l));

		Activity readEntity = activityRepository.findOne(saved.getId());

		Assert.assertThat(readEntity, new Equals(saved));
	}

	@Test
	public void when_relate_two_activities_via_activitys_relation_expect_activity_loaded_contains_relation()
	{
		Activity activity1 = new ActivityBuilder().build();
		Activity activity2 = new ActivityBuilder().build();

		Activity mergedActivity1 = activityRepository.save(activity1);
		Activity mergedActivity2 = activityRepository.save(activity2);

		mergedActivity1.relateActivity(mergedActivity2);

		//activityRepository.save(relateActivity);
		activityRepository.save(mergedActivity1);

		Activity readActivity1 = activityRepository.findOne(mergedActivity1.getId());

		Assert.assertThat(readActivity1.getRelations().size(), IsEqual.equalTo(1));

		Assert.assertThat(readActivity1.getRelations(), IsIterableContainingInAnyOrder.containsInAnyOrder(new LambdaMatcher<>(r ->
			r.getRelatedActivities().contains(mergedActivity1), "cannot find activity 1")));
		Assert.assertThat(readActivity1.getRelations(), IsIterableContainingInAnyOrder.containsInAnyOrder(new LambdaMatcher<>(r ->
			r.getRelatedActivities().contains(mergedActivity2), "cannot find activity 2")));
	}

	@Test
	public void when_relate_two_activities_via_activity1_expect_activity2_con() {
		Activity activity1 = new ActivityBuilder().build();
		Activity activity2 = new ActivityBuilder().build();

		Activity mergedActivity1 = activityRepository.save(activity1);
		Activity mergedActivity2 = activityRepository.save(activity2);

		mergedActivity1.relateActivity(mergedActivity2);

		activityRepository.save(mergedActivity1);

		Activity readActivity2 = activityRepository.findOne(mergedActivity2.getId());

		Assert.assertThat(readActivity2.getRelations().size(), IsEqual.equalTo(1));

		Assert.assertThat(readActivity2.getRelations(), IsIterableContainingInAnyOrder.containsInAnyOrder(new LambdaMatcher<>(r ->
			r.getRelatedActivities().contains(mergedActivity1), "cannot find activity 1")));

		Assert.assertThat(readActivity2.getRelations(), IsIterableContainingInAnyOrder.containsInAnyOrder(new LambdaMatcher<>(r ->
			r.getRelatedActivities().contains(mergedActivity2), "cannot find activity 2")));
	}

	public void when_relate_two_activities_via_activitys_relation_list_expect_both_activities_contains_activityrelation_referencing_both_activities()
	{
		Activity activity1 = new ActivityBuilder().build();
		Activity activity2 = new ActivityBuilder().build();

		Activity mergedActivity1 = activityRepository.save(activity1);
		Activity mergedActivity2 = activityRepository.save(activity2);

		activityRepository.save(new ActivityRelation(mergedActivity1, mergedActivity2));

		Activity readActivity1 = activityRepository.findOne(mergedActivity2.getId());
		Activity readActivity2 = activityRepository.findOne(mergedActivity2.getId());

		Assert.assertThat(readActivity1.getRelations().size(), IsEqual.equalTo(1));

		Assert.assertThat(readActivity1.getRelations(), IsIterableContainingInAnyOrder.containsInAnyOrder(new LambdaMatcher<>(r ->
			r.getRelatedActivities().contains(mergedActivity1), "cannot find activity 1")));

		Assert.assertThat(readActivity1.getRelations(), IsIterableContainingInAnyOrder.containsInAnyOrder(new LambdaMatcher<>(r ->
			r.getRelatedActivities().contains(mergedActivity2), "cannot find activity 2")));

		Assert.assertThat(readActivity2.getRelations().size(), IsEqual.equalTo(1));

		Assert.assertThat(readActivity2.getRelations(), IsIterableContainingInAnyOrder.containsInAnyOrder(new LambdaMatcher<>(r ->
			r.getRelatedActivities().contains(mergedActivity1), "cannot find activity 1")));

		Assert.assertThat(readActivity2.getRelations(), IsIterableContainingInAnyOrder.containsInAnyOrder(new LambdaMatcher<>(r ->
			r.getRelatedActivities().contains(mergedActivity2), "cannot find activity 2")));
	}

	@Test
	public void when_find_activity_relation_expect_references_both_activities()
	{
		Assert.fail();
	}

	@Test
	public void when_start_activity_expect_domain_event_persisted() throws Exception {
		Activity activity1 = new ActivityBuilder().build();
		activity1.start();

		Activity activitySaved = activityRepository.save(activity1);

		Assert.assertThat(activity1.getEvents(), IsIterableContainingInAnyOrder.containsInAnyOrder(activitySaved.getEvents()));
	}

	@Test
	public void when_relate_activity_expect_domain_event_for_both_activities_persisted() throws Exception {

		Assert.fail();
	}

	/*	private JpaActivityRepository CreateRepository() {
		return new JpaActivityRepository();
	}*/
}
