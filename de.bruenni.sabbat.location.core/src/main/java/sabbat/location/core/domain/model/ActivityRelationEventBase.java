package sabbat.location.core.domain.model;

import java.time.Instant;

/**
 * Created by bruenni on 16.03.17.
 */
public class ActivityRelationEventBase {
	protected Long id;
	private Instant timestamp;

	public ActivityRelationEventBase(Long id, Instant timestamp) {
		this.id = id;
		this.timestamp = timestamp;
	}

	public Long getId() {
		return id;
	}

	public Instant getTimestamp() {
		return timestamp;
	}
}
