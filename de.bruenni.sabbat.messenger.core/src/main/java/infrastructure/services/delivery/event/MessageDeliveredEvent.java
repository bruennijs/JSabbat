package infrastructure.services.delivery.event;

import java.util.Date;

/**
 * Created by bruenni on 08.06.16.
 */
public class MessageDeliveredEvent {
    String id;
    Date timestamp;

    public String getId() {
        return id;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
