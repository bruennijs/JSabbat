package sabbat.messenger.core.domain.messenger.events;

import infrastructure.common.event.implementation.Event;
import sabbat.messenger.core.infrastructure.delivery.DeliveryRequestResult;

import java.util.UUID;

/**
 * Created by bruenni on 16.06.16.
 */
public class DeliveryRequestResultReceivedEvent extends Event {
    private DeliveryRequestResult result;

    public DeliveryRequestResultReceivedEvent(UUID messageId, DeliveryRequestResult result) {
        super(messageId);
        this.result = result;
    }

    public DeliveryRequestResult getResult() {
        return result;
    }
}
