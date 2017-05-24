package sabbat.location.core.domain.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import infrastructure.parser.IDtoParser;
import infrastructure.parser.JsonDtoParser;
import infrastructure.parser.ParserException;
import infrastructure.parser.SerializingException;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.ActivityEvent;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by bruenni on 16.03.17.
 */
@Entity
@DiscriminatorValue(value = "2")
public class ActivityRelationCreatedEvent extends ActivityEvent {

	@Transient
	private ActivityRelationCreatedEvent.Attributes attributes;

	private static IDtoParser jsonParser = new JsonDtoParser();

	public ActivityRelationCreatedEvent() {
	}

	private Attributes parseAttributes() throws ParserException {
		return jsonParser.parse(getDocument(), Attributes.class);
	}

	/**
	 * Constructor
	 * @param timestamp
	 */
	public ActivityRelationCreatedEvent(Activity aggregate, Date timestamp, Long relatedActivityId) {
		super(aggregate, timestamp);
		this.attributes = new Attributes(relatedActivityId);

		try {
			serializeAttributes();
		} catch (SerializingException e) {
			e.printStackTrace();
		}
	}

	private void serializeAttributes() throws SerializingException {
		this.setDocument(this.jsonParser.serialize(this.attributes));
	}

	/**
	 * Created by bruenni on 16.03.17.
	 */
	public class Attributes {
		@JsonProperty("relatedId")
		protected Long relatedActivityId;

		/**
		 * JSON deserialization.
		 */
		public Attributes() {
		}

		/**
		 * Constructor
		 */
		public Attributes(Long relatedActivity) {
			this.relatedActivityId = relatedActivity;
		}

		/**
		 * Gets related activity id
		 * @return
		 */
		public Long getRelatedActivityId() {
			return relatedActivityId;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Attributes that = (sabbat.location.core.domain.events.ActivityRelationCreatedEvent.Attributes) o;

			return relatedActivityId.equals(that.relatedActivityId);
		}

		@Override
		public int hashCode() {
			return relatedActivityId.hashCode();
		}
	}
}
