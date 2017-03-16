package sabbat.location.core.domain.model.relation;

import infrastructure.common.event.IEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Created by bruenni on 14.03.17.
 * An relation is created if two users have an relation cause of
 * 1) they are in the same group and are in an activity simultanously
 * 2) their euclidean distance between them has been under a specific threshold
 *
 */
public class ActivityRelation {
	private long id;
	private Long activityId1;
	private Long activityId2;

	private List<ActivityRelationEventBase> events;

	/**
	 * Constructor
	 * @param id
	 */
	public ActivityRelation(long id, Long activityId1, Long activityId2) {
		this.id = id;
		this.activityId1 = activityId1;
		this.activityId2 = activityId2;
		events = new ArrayList<ActivityRelationEventBase>();
	}

	public ActivityRelation(long id, List<ActivityRelationEventBase> events) {
		this.id = id;
		this.events = events;
	}

	public long getId() {
		return id;
	}

	/**
	 * Adds relation event.
	 * @param event
	 */
	public void addEvent(ActivityRelationEventBase event)
	{
		events.add(event);
	}

	/**
	 * Calculates latest distance
	 * @return
	 * @throws Exception
	 */
	public double latestDistance() throws Exception
	{
			Optional<ActivityRelationEventBase> latestEvent = events
				.stream()
				.filter(e -> e instanceof NewDistanceEvent)
				.max(Comparator.comparing(e -> e.getTimestamp()));

			if (latestEvent.isPresent())
				return ((NewDistanceEvent) latestEvent.get()).getDistance();

			throw new Exception("latest distance could not be calculated!");
	}
}
