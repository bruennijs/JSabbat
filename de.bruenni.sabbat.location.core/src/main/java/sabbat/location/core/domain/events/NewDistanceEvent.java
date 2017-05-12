package sabbat.location.core.domain.events;

import sabbat.location.core.domain.model.*;

import java.time.Instant;

/**
 * Created by bruenni on 16.03.17.
 */
public class NewDistanceEvent extends sabbat.location.core.domain.model.ActivityRelationEventBase {
	private double distance;

	public NewDistanceEvent(Long id, Instant timestamp, double distance) {
		super(id, timestamp);
		this.distance = distance;
	}

	public double getDistance() {
		return distance;
	}
}
