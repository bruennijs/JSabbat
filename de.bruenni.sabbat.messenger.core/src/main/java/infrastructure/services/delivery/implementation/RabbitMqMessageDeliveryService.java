package infrastructure.services.delivery.implementation;

import domain.aggregates.Message;
import infrastructure.common.event.IEvent;
import infrastructure.services.delivery.DeliveryRequestResult;
import infrastructure.services.delivery.IMessageDeliveryService;
import infrastructure.services.delivery.event.MessageDeliveredEvent;
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

    @Override
    public Observable<IEvent> getEvent() {
        return eventObservable;
    }

    /**
     * Requests and triggers a message delivery.
     * Async result contains information about the success of the request not whether message
     * has been delivered to a user.
     * Delivered events are handled the corresponding observable.
     * @param msg
     * @return
     */
    @Override
    public Future<DeliveryRequestResult> requestDelivery(Message msg)
    {
        CompletableFuture<DeliveryRequestResult> future = new CompletableFuture<>();

        future.complete(new DeliveryRequestResult(false));

        return future;
    }
}
