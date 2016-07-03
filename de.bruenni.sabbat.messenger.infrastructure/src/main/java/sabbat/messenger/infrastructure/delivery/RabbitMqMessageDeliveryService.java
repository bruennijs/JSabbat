package sabbat.messenger.infrastructure.delivery;

import infrastructure.common.event.IEvent;
import sabbat.messenger.core.infrastructure.delivery.DeliveryRequest;
import sabbat.messenger.core.infrastructure.delivery.DeliveryRequestResult;
import rx.Observable;
import rx.subjects.PublishSubject;
import sabbat.messenger.core.infrastructure.delivery.IMessageDeliveryService;

import java.util.concurrent.CompletableFuture;

/**
 * Created by bruenni on 05.06.16.
 * Requests delivery of messages. This process is a long running process
 * and to fulfill avialablity the events indicating success of delivery is not
 * part of this implementation
 */
public class RabbitMqMessageDeliveryService implements IMessageDeliveryService {

    private Observable<IEvent> eventObservable = PublishSubject.create();


    /**
     * Requests and triggers a message delivery.
     * Async deliverySuccessful contains information about the success of the request not whether message
     * has been delivered to a user.
     * Delivered events are handled the corresponding observable.
     * @param request
     * @return
     */
    @Override
    public CompletableFuture<DeliveryRequestResult> requestDelivery(DeliveryRequest request) {
        CompletableFuture<DeliveryRequestResult> future = new CompletableFuture<>();

        future.complete(new DeliveryRequestResult(request.getCorrelationId(), Boolean.FALSE));

        return future;
    }
}
