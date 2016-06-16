package sabbat.messenger.core.domain.events;

import infrastructure.common.event.IEvent;
import infrastructure.common.event.implementation.Event;
import infrastructure.services.delivery.DeliveryResponse;

import java.util.Date;
import java.util.UUID;

/**
 * Created by bruenni on 16.06.16.
 */
public class DeliveryResponseReceivedEvent extends Event implements IEvent {
    private DeliveryResponse deliveryResponse;

    public DeliveryResponseReceivedEvent(DeliveryResponse deliveryResponse) {
        super();
        this.deliveryResponse = deliveryResponse;
    }

    /**
     * @return response
     */
    public DeliveryResponse getDeliveryResponse() {
        return deliveryResponse;
    }
}
