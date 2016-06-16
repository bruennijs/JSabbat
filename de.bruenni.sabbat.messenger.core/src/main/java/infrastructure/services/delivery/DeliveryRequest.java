package infrastructure.services.delivery;

import sabbat.messenger.core.domain.aggregates.identity.User;

import java.util.UUID;

/**
 * Created by bruenni on 16.06.16.
 */
public abstract class DeliveryRequest {
    public UUID requestId;

    public User sender;

    public User recipient;

    public DeliveryRequest(User sender, User recipient, UUID requestId) {
        this.recipient = recipient;
        this.requestId = requestId;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }
}
