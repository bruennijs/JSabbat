package builder;

import sabbat.messenger.core.infrastructure.delivery.DeliveryResponse;

/**
 * Created by bruenni on 19.06.16.
 */
public class DeliveryResponseBuilder{
    private boolean isDeliverySuccessful = true;

    public DeliveryResponseBuilder() {
    }

    public DeliveryResponse Build() {
        return new DeliveryResponse(isDeliverySuccessful, "correlationid", "restclient 1.4.5.2");
    }

    public DeliveryResponseBuilder WithSuccess(boolean value) {
        isDeliverySuccessful = value;
        return this;
    }
}
