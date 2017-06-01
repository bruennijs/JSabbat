package sabbat.location.core.unittest.domain.model;

import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.LoggerFactory;
import sabbat.location.core.builder.ActivityBuilder;
import sabbat.location.core.domain.events.activity.ActivityEvent;
import sabbat.location.core.domain.events.activity.ActivityRelationCreatedEvent;
import sabbat.location.core.domain.events.activity.ActivityStartedEvent;
import sabbat.location.core.domain.events.activity.ActivityStoppedEvent;
import sabbat.location.core.domain.model.Activity;
import test.matcher.LambdaMatcher;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by bruenni on 09.04.17.
 */
@RunWith(JUnit4.class)
public class ActivityTest {

	org.slf4j.Logger log = LoggerFactory.getLogger(ActivityTest.class);

	@Test
	public void when_relate_activity_expect_domain_event_for_both_activities_created() throws Exception {
		Activity activity1 = new ActivityBuilder().build();
		Activity activity2 = new ActivityBuilder().build();
		List<ActivityEvent> activityEvents = activity1.relateActivity(activity2);

		Assert.assertThat(activityEvents, Matchers.contains(Matchers.instanceOf(ActivityRelationCreatedEvent.class),Matchers.instanceOf(ActivityRelationCreatedEvent.class)));

		List<ActivityRelationCreatedEvent> concreteEvents = activityEvents.stream().map(e -> (ActivityRelationCreatedEvent)e).collect(Collectors.toList());

		Assert.assertThat(concreteEvents, Matchers.hasItem(
			LambdaMatcher.<ActivityRelationCreatedEvent>isMatching(e -> e.getAggregate() == activity1 && e.getAttributes().getRelatedActivityId().equals(activity2.getId()))
		));

		Assert.assertThat(concreteEvents, Matchers.hasItem(
			LambdaMatcher.<ActivityRelationCreatedEvent>isMatching(e -> e.getAggregate() == activity2 && e.getAttributes().getRelatedActivityId().equals(activity1.getId()))
		));
	}

	@Test
	public void when_relate_activity_expect_getrelatedactivities_returns_related_activity_only() throws Exception {
		Activity activity1 = new ActivityBuilder().build();
		Activity activity2 = new ActivityBuilder().build();

		activity1.relateActivity(activity2);

		// assert activity1 relates to activity2
		Assert.assertThat(activity1.getRelatedActivities(), Matchers.contains(IsEqual.equalTo(activity2)));

		// assert activity2 relates to activity1
		Assert.assertThat(activity2.getRelatedActivities(), Matchers.contains(IsEqual.equalTo(activity1)));
	}

	@Test
	@Ignore("Cannot run cause ID of ActivityRelation instance is still null cause not persisted. Equals() will fail!")
	public void when_relate_activity_expect_relations_of_both_are_identical() throws Exception {
		Activity activity1 = new ActivityBuilder().build();
		Activity activity2 = new ActivityBuilder().build();

		activity1.relateActivity(activity2);

		Assert.assertThat(activity1.getRelations(), Matchers.contains(activity2.getRelations()));
	}

	@Test
	public void when_start_activity_expect_domain_event_created() throws Exception {
		Activity activity1 = new ActivityBuilder().build();
		ActivityStartedEvent startedEvent = activity1.start();

		Assert.assertThat(activity1, Matchers.is(startedEvent.getAggregate()));
		Assert.assertThat(activity1.getStarted(), IsEqual.equalTo(startedEvent.getCreatedOn()));
	}

	@Test
	public void when_stop_expect_domain_ActivityStoppedEvent_created() throws Exception {
		Activity activity1 = new ActivityBuilder().build();
		activity1.start();
		ActivityEvent stopEvent = activity1.stop();

		Assert.assertThat(stopEvent, Matchers.instanceOf(ActivityStoppedEvent.class));
	}

	@Test
	public void regex() throws Exception {
		//
		String input = "cn=ethe1234+serialNumber=2";
		String patternStr = "^cn\\=(?<serialno>.*)\\+serialNumber\\=(?<serialnocount>\\d+)$";

		//log.info(patternStr);

		// does pattern match
		Assert.assertThat(input.matches(patternStr), IsEqual.equalTo(true));

		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(input);

		Assert.assertEquals(true, matcher.find());

		String serialno = matcher.group("serialno");
		int serialnocount = Integer.parseInt(matcher.group("serialnocount"), 10);

		Assert.assertThat(serialno, IsEqual.equalTo("ethe1234"));
		Assert.assertThat(serialnocount, IsEqual.equalTo(2));
	}
}
