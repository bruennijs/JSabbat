package infrastructure.services.delivery;

import domain.aggregates.Message;
import infrastructure.common.event.IEvent;
import rx.Observable;

import java.util.concurrent.Future;

/**
 * Created by bruenni on 05.06.16.
 */
public interface IMessageDeliveryService {

    /**
     * Requests and triggers a message delivery.
     * Async result contains information about the success of the request not whether message
     * has been delivered to a user.
     * Delivered events are handled the corresponding observable.
     * @param msg
     * @return
     */
    Future<DeliveryRequestResult> requestDelivery(Message msg);
}
