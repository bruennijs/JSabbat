package infrastructure.services.delivery.event;

import infrastructure.common.event.implementation.Event;

import java.util.Date;

/**
 * Created by bruenni on 08.06.16.
 */
public class MessageDeliveredEvent extends Event {

    public MessageDeliveredEvent(String id, Date timestamp) {
        super(id, timestamp);
    }
}
