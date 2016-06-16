package infrastructure.services.delivery;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Created by bruenni on 05.06.16.
 */
public interface IMessageDeliveryService {

    /**
     * Requests and triggers a message delivery.
     * Async deliverySuccessful contains information about the success of the request not whether message
     * has been delivered to a user.
     * Delivered events are handled the corresponding observable.
     * @param request delivery request
     * @return
     */
    CompletableFuture<DeliveryRequestResult> requestDelivery(DeliveryRequest request);
}
