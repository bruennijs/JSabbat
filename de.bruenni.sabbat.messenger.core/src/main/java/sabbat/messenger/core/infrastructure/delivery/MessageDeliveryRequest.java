package sabbat.messenger.core.infrastructure.delivery;

import sabbat.messenger.core.domain.messenger.aggregates.IMessage;

/**
 * Created by bruenni on 16.06.16.
 */
public class MessageDeliveryRequest extends DeliveryRequest {

    public MessageDeliveryRequest(IMessage message, String requestId) {
        super(message, requestId);
    }
}
