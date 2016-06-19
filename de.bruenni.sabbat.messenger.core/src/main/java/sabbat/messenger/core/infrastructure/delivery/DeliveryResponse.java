package sabbat.messenger.core.infrastructure.delivery;

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
    private String clientInfo;

    /**
     *
     * @param correlationId
     */
    public DeliveryResponse(boolean successful, String correlationId, String clientInfo)
    {
        super(correlationId);
        deliverySuccessful = successful;
        this.clientInfo = clientInfo;
    }

    /**
     * Gets human readable info about the client message has been sent to.
     * @return
     */
    public String getClientInfo() {
        return clientInfo;
    }

    public boolean isDeliverySuccessful() {
        return deliverySuccessful;
    }

    @Override
    public String toString() {
        return "DeliveryResponse{" +
                "deliverySuccessful=" + deliverySuccessful +
                ", clientInfo='" + clientInfo + '\'' +
                "} " + super.toString();
    }
}
