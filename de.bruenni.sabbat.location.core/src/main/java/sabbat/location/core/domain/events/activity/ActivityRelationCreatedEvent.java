package sabbat.location.core.domain.events.activity;

import com.fasterxml.jackson.annotation.JsonProperty;
import infrastructure.parser.IDtoParser;
import infrastructure.parser.JsonDtoParser;
import infrastructure.parser.ParserException;
import infrastructure.parser.SerializingException;
import sabbat.location.core.domain.events.activity.converter.JsonToAttributeConverter;
import sabbat.location.core.domain.model.Activity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by bruenni on 16.03.17.
 */
@Entity
@DiscriminatorValue(value = "2")
public class ActivityRelationCreatedEvent extends ActivityEvent {

	@Convert(converter = AttributesConverter.class)
	@Column(name = "document")
	private ActivityRelationCreatedEvent.Attributes attributes;

	public ActivityRelationCreatedEvent() {
	}

	/**
	 * Constructor
	 * @param timestamp
	 */
	public ActivityRelationCreatedEvent(Activity aggregate, Date timestamp, Long relatedActivityId) {
		super(aggregate, timestamp);
		this.attributes = new Attributes(relatedActivityId);
	}

	public Attributes getAttributes() {
		return attributes;
	}

	@Override
	public String toString() {
		return "ActivityRelationCreatedEvent{" +
			"attributes=" + attributes +
			"} " + super.toString();
	}

	/**
	 * Created by bruenni on 16.03.17.
	 */
	public class Attributes {
		@JsonProperty("relatedId")
		private Long relatedActivityId;

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

			Attributes that = (ActivityRelationCreatedEvent.Attributes) o;

			return relatedActivityId.equals(that.relatedActivityId);
		}

		@Override
		public int hashCode() {
			return relatedActivityId.hashCode();
		}

		@Override
		public String toString() {
			return "Attributes{" +
				"relatedActivityId=" + relatedActivityId +
				'}';
		}
	}

	/**
	 * Converts JSON to type
	 */
	public static class AttributesConverter extends JsonToAttributeConverter<ActivityRelationCreatedEvent.Attributes> {
		public AttributesConverter() {
			super(ActivityRelationCreatedEvent.Attributes.class);
		}
	}
}
