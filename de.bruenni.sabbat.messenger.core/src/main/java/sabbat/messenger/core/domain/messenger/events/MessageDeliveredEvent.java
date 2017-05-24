package sabbat.messenger.core.domain.messenger.events;

import infrastructure.common.event.Event;
import infrastructure.common.event.implementation.EventBase;

import java.util.Date;
import java.util.UUID;

/**
 * Created by bruenni on 08.06.16.
 */
public class MessageDeliveredEvent extends EventBase {

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
