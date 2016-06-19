package sabbat.messenger.core.infrastructure.delivery;

import infrastructure.common.gateway.Request;
import sabbat.messenger.core.domain.aggregates.identity.User;

import java.util.UUID;

/**
 * Created by bruenni on 16.06.16.
 */
public abstract class DeliveryRequest extends Request<String> {
    public User sender;

    public User recipient;

    public DeliveryRequest(User sender, User recipient, String requestId) {
        super(requestId);
        this.recipient = recipient;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }
}
