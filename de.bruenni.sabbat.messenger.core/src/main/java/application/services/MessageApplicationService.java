package sabbat.messenger.core.application.services;

import domain.aggregates.Message;
import infrastructure.common.event.*;
import infrastructure.services.delivery.event.MessageDeliveredEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.CrudRepository;

import org.apache.logging.log4j.*;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.concurrent.Future;

/**
 * Created by bruenni on 28.05.16.
 */
public class MessageApplicationService implements IEventHandler<IEvent> {
    static final Logger logger = LogManager.getLogger(MessageApplicationService.class.getName());

    private IDomainEventBus<IEvent> eventBus;
    private CrudRepository<Message, Long> messageRepository;
    private Type[] events = new Type[] { MessageDeliveredEvent.class };

    public MessageApplicationService(IDomainEventBus<IEvent> eventBus, CrudRepository<Message, Long> messageRepository) {
        this.eventBus = eventBus;
        this.messageRepository = messageRepository;
        Subscribe(this.eventBus);
    }

    public Future<Message> send(String toUserName, String content)
    {
        logger.debug("send [to=" + toUserName + ",content=" + content +"]");

        return null;
    }

    private void Subscribe(IDomainEventBus<IEvent> eventBus) {
        eventBus
                .subscribe()
                .filter(e -> { return Arrays.stream(events).anyMatch(se -> se.getClass().equals(e.getClass())); })
                .subscribe(event -> HandleEvent(event));
    }

    private void HandleEvent(IEvent event) {
        logger.debug("OnEvent [" + event.toString() + "]");

        if (event instanceof MessageDeliveredEvent)
        {
            Message message = messageRepository.findOne(new Long(event.getId()));
            message.OnEvent(event);
        }
    }

    @Override
    public void OnEvent(IEvent event) {
        logger.debug("OnEvent IEventHandler [" + event.toString() + "]");
    }

    @Override
    public Type[] getSupportedEvents() {
        return events;
    }
}
