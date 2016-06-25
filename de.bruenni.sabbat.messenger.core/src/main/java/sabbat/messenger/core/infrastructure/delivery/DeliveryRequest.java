package sabbat.messenger.core.infrastructure.delivery;

import infrastructure.common.gateway.Request;
import sabbat.messenger.core.domain.messenger.ValueObjects.User;
import sabbat.messenger.core.domain.messenger.aggregates.IMessage;

/**
 * Created by bruenni on 16.06.16.
 */
public abstract class DeliveryRequest extends Request<String> {

    private IMessage message;

    public DeliveryRequest(IMessage message, String requestId) {
        super(requestId);
        this.message = message;
    }

    public IMessage getMessage() {
        return message;
    }
}
