package sabbat.messenger.core.domain.messenger.events;

import infrastructure.common.event.implementation.EventBase;
import sabbat.messenger.core.infrastructure.delivery.DeliveryRequestResult;

import java.util.UUID;

/**
 * Created by bruenni on 16.06.16.
 */
public class DeliveryRequestResultReceivedEvent extends EventBase {
    private DeliveryRequestResult result;

    public DeliveryRequestResultReceivedEvent(UUID messageId, DeliveryRequestResult result) {
        super(messageId);
        this.result = result;
    }

    public DeliveryRequestResult getResult() {
        return result;
    }
}
