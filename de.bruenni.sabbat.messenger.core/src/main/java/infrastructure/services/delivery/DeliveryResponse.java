package infrastructure.services.delivery;

import infrastructure.common.gateway.Response;

/**
 * Created by bruenni on 16.06.16.
 */
public class DeliveryResponse extends Response<String> {
    /**
     * True means that a delivery service could send and received an ack
     * by the client. False means that service sent the message but did not
     * receive an acknowledgement.
     */
    public boolean deliverySuccessful;

    /**
     *
     * @param correlationId
     */
    public DeliveryResponse(String correlationId)
    {
        super(correlationId);
    }


    public boolean isDeliverySuccessful() {
        return deliverySuccessful;
    }
}
