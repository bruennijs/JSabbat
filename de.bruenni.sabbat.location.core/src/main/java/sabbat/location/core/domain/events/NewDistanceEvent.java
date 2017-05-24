package sabbat.location.core.domain.events;

import sabbat.location.core.domain.model.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.time.Instant;
import java.util.Date;

/**
 * Created by bruenni on 16.03.17.
 */
@Entity
@DiscriminatorValue(value = "4")
public class NewDistanceEvent extends ActivityEvent {
	@Transient
	private double distance;

	public NewDistanceEvent(Activity activity1, Activity activity2, Date timestamp, double distance) {
		super(activity1, timestamp);
		this.distance = distance;
	}

	public double getDistance() {
		return distance;
	}
}
