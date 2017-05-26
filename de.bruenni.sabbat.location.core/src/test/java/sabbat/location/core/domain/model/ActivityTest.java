package sabbat.location.core.domain.model;

import infrastructure.common.event.IEvent;
import infrastructure.util.IterableUtils;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.LoggerFactory;
import sabbat.location.core.builder.ActivityBuilder;
import sabbat.location.core.domain.events.activity.ActivityRelationCreatedEvent;
import sabbat.location.core.domain.events.activity.ActivityStartedEvent;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		IEvent[] domainEvents = activity1.relateActivity(activity2);

		//Assert.assertThat(ActivityRelationCreatedEvent.class, IsIn.isIn(Arrays.stream(domainEvents).map(e -> e.getClass()).toArray()));
		Assert.assertThat(2, IsEqual.equalTo(domainEvents.length));
		ActivityRelationCreatedEvent event1 = (ActivityRelationCreatedEvent) domainEvents[0];
		ActivityRelationCreatedEvent event2 = (ActivityRelationCreatedEvent) domainEvents[1];

		Assert.fail();
	}

	@Test
	public void when_start_activity_expect_domain_event_created() throws Exception {
		Activity activity1 = new ActivityBuilder().build();
		IEvent event = activity1.start();

		Assert.assertThat(ActivityStartedEvent.class, IsEqual.equalTo(event.getClass()));

		List<IEvent<Long, Long>> activityEvents = IterableUtils.toList(activity1.getEvents());
		Assert.assertThat(1, IsEqual.equalTo(activityEvents.size()));
		IEvent<Long, Long> domainEvent = activityEvents.get(0);
		Assert.assertThat(activity1.getId(), IsEqual.equalTo(domainEvent.getAggregateId()));
		Assert.assertThat(activity1.getStarted(), IsEqual.equalTo(domainEvent.getTimestamp()));
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
