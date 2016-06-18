package infrastructure.services.delivery.implementation;

import infrastructure.common.event.IEvent;
import infrastructure.services.delivery.DeliveryRequest;
import infrastructure.services.delivery.DeliveryRequestResult;
import infrastructure.services.delivery.IMessageDeliveryService;
import rx.Observable;
import rx.subjects.PublishSubject;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

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
