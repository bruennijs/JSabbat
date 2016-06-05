package sabbat.messenger.core.application.services;

import domain.aggregates.Message;
import infrastructure.common.event.*;
import infrastructure.common.event.implementation.Event;
import rx.Observable;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by bruenni on 28.05.16.
 */
public class MessageApplicationService {
    private IDomainEventBus<IEvent> eventBus;
    private Observable<IEvent> eventSubscription;

    public MessageApplicationService(IDomainEventBus<IEvent> eventBus) {
        this.eventBus = eventBus;
        Subscribe(this.eventBus);
    }

    public Future<Message> send(String to, String content)
    {
        System.out.println("send[to=" + to + ",content=" + content + "]");
        /*java.util.concurrent.CompletableFuture<Message>();
        Future<Message> future = new Future<Message>();*/
        return null;
    }

    private void Subscribe(IDomainEventBus<IEvent> eventBus) {
        eventSubscription = eventBus
                .subscribe()
                .filter(e -> { return e instanceof Event; });
    }
}
