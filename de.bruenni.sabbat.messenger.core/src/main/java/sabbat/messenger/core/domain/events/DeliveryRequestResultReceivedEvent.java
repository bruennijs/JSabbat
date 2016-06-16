package sabbat.messenger.core.domain.events;

import infrastructure.common.event.IEvent;
import infrastructure.common.event.implementation.Event;
import infrastructure.services.delivery.DeliveryRequestResult;

/**
 * Created by bruenni on 16.06.16.
 */
public class DeliveryRequestResultReceivedEvent extends Event {
    private DeliveryRequestResult result;

    public DeliveryRequestResultReceivedEvent(DeliveryRequestResult result) {
        this.result = result;
    }

    public DeliveryRequestResult getResult() {
        return result;
    }
}
