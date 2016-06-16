package infrastructure.services.delivery;

import java.util.UUID;

/**
 * Created by bruenni on 16.06.16.
 */
public class DeliveryResponse {
    public UUID requestId;

    /**
     * True means that a delivery service could send and received an ack
     * by the client. False means that service sent the message but did not
     * receive an acknowledgement.
     */
    public boolean deliverySuccessful;

    /**
     *
     * @param requestId
     */
    public DeliveryResponse(UUID requestId) {
        this.requestId = requestId;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public boolean isDeliverySuccessful() {
        return deliverySuccessful;
    }
}
