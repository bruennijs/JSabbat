package sabbat.messenger.core.application.services;

import infrastructure.common.event.*;

/**
 * Created by bruenni on 28.05.16.
 */
public class MessageApplicationService {
    private IDomainEventBus<IEvent> eventBus;

    public MessageApplicationService(IDomainEventBus<IEvent> eventBus) {
        this.eventBus = eventBus;
    }
}
