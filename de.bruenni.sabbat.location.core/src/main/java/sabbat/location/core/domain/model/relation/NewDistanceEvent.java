package sabbat.location.core.domain.model.relation;

import java.time.Instant;

/**
 * Created by bruenni on 16.03.17.
 */
public class NewDistanceEvent extends ActivityRelationEventBase {
	private double distance;

	public NewDistanceEvent(Long id, Instant timestamp, double distance) {
		super(id, timestamp);
		this.distance = distance;
	}

	public double getDistance() {
		return distance;
	}
}
