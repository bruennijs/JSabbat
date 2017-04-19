package sabbat.location.core.domain.model;

import com.hazelcast.util.IterableUtil;
import infrastructure.common.event.IEvent;
import infrastructure.util.IterableUtils;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import sabbat.location.core.builder.ActivityBuilder;
import sabbat.location.core.domain.events.ActivityRelationCreatedEvent;
import sabbat.location.core.domain.events.ActivityStartedEvent;

import java.util.List;

/**
 * Created by bruenni on 09.04.17.
 */
@RunWith(JUnit4.class)
public class ActivityTest {


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
}
