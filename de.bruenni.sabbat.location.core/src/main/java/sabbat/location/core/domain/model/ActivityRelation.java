package sabbat.location.core.domain.model;

import infrastructure.util.IterableUtils;
import sabbat.location.core.domain.events.activity.NewDistanceEvent;
import sabbat.location.core.domain.events.activity.ActivityEvent;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by bruenni on 14.03.17.
 * An relation is created if two users have an relation cause of
 * 1) they are in the same group and are in an activity simultanously
 * 2) their euclidean distance between them has been under a specific threshold
 *
 */
@Entity
@Table(name = "activityrelation", schema = "loc")
public class ActivityRelation implements Serializable {
	@Id
	@SequenceGenerator(name = "activityrelation_seq", sequenceName = "loc.activityrelation_id_seq",  initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activityrelation_seq")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY,
				cascade = CascadeType.REFRESH)
	@JoinColumn(name = "activityid1")
	private Activity activity1;

	@ManyToOne(fetch = FetchType.LAZY,
				cascade = CascadeType.REFRESH)
	@JoinColumn(name = "activityid2")
	private Activity activity2;

	public ActivityRelation() {
	}


	public ActivityRelation(Activity activity1, Activity activity2) {
		this.activity1 = activity1;
		this.activity2 = activity2;
	}

	public long getId() {
		return id;
	}

	/**
	 * Calculates latest distance
	 * @return
	 * @throws Exception
	 */
	public double latestDistance() throws Exception
	{
			Optional<ActivityEvent> latestEvent = IterableUtils.stream(activity1.getEvents())
				.filter(e -> e instanceof NewDistanceEvent)
				.max(Comparator.comparing(e -> e.getCreatedOn()));

			if (latestEvent.isPresent())
				return ((NewDistanceEvent) latestEvent.get()).getDistance();

			throw new Exception("latest distance could not be calculated!");
	}

	/**
	 * List of both related activities
	 * @return
	 */
	public List<Activity> getRelatedActivities()
	{
		return Arrays.asList(this.activity1, this.activity2);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ActivityRelation that = (ActivityRelation) o;

		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return "ActivityRelation{" +
			"id=" + id +
			", activity1.id=" + activity1.getId() +
			", activity2.id=" + activity2.getId() +
			'}';
	}
}
