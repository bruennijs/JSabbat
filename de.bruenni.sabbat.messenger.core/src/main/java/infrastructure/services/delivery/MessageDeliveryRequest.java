package infrastructure.services.delivery;

import sabbat.messenger.core.domain.aggregates.identity.User;

import java.util.UUID;

/**
 * Created by bruenni on 16.06.16.
 */
public class MessageDeliveryRequest extends DeliveryRequest {

    private String content;

    public MessageDeliveryRequest(User sender, User recipient, UUID requestId, String content) {
        super(sender, recipient, requestId);
        this.sender = sender;
        this.content = content;
    }

    /**
     * @return content of the message
     */
    public String getContent() {
        return content;
    }
}
