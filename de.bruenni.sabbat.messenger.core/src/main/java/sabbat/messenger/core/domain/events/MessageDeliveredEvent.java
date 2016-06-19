package sabbat.messenger.core.domain.events;

import infrastructure.common.event.implementation.Event;

import java.util.Date;
import java.util.UUID;

/**
 * Created by bruenni on 08.06.16.
 */
public class MessageDeliveredEvent extends Event {

    /**
     * Constructor.
     * @param id
     * @param messageId
     * @param timestamp
     */
    public MessageDeliveredEvent(UUID id, UUID messageId, Date timestamp) {
        super(id, messageId, timestamp);
    }
}
