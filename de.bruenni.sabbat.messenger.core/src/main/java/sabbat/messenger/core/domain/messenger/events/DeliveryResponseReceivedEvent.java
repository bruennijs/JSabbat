package sabbat.messenger.core.domain.messenger.events;

import infrastructure.common.event.implementation.EventBase;
import sabbat.messenger.core.infrastructure.delivery.DeliveryResponse;

import java.util.UUID;

/**
 * Created by bruenni on 16.06.16.
 */
public class DeliveryResponseReceivedEvent extends EventBase {
    private DeliveryResponse deliveryResponse;

    /**
     * Constructor.
     * @param messageId
     * @param deliveryResponse
     */
    public DeliveryResponseReceivedEvent(UUID messageId, DeliveryResponse deliveryResponse) {
        super(messageId);
        this.deliveryResponse = deliveryResponse;
    }

    /**
     * @return response
     */
    public DeliveryResponse getDeliveryResponse() {
        return deliveryResponse;
    }
}
