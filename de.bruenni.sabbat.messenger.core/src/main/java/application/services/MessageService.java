package application.services;

import java.util.concurrent.Executors;
import de.bruenni.infrastructure.common.event.*;

/**
 * Created by bruenni on 28.05.16.
 */
public class MessageService {
    private IDomainEventBus<IEvent> eventBus;

    public MessageService(IDomainEventBus<IEvent> eventBus) {
        this.eventBus = eventBus;
    }
}
