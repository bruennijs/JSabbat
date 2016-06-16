package sabbat.messenger.core.application.services;

import infrastructure.persistence.IUserRepository;
import infrastructure.services.delivery.DeliveryRequestResult;
import sabbat.messenger.core.domain.aggregates.Message;
import sabbat.messenger.core.domain.aggregates.identity.User;
import infrastructure.common.event.*;
import infrastructure.services.delivery.IMessageDeliveryService;
import infrastructure.services.delivery.MessageDeliveryRequest;
import infrastructure.services.delivery.DeliveryResponse;
import org.springframework.data.repository.CrudRepository;

import org.apache.logging.log4j.*;
import sabbat.messenger.core.domain.events.DeliveryRequestResultReceivedEvent;
import sabbat.messenger.core.domain.events.DeliveryResponseReceivedEvent;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Created by bruenni on 28.05.16.
 */
public class MessageApplicationService implements IEventHandler<IEvent> {
    static final Logger logger = LogManager.getLogger(MessageApplicationService.class.getName());

    private IDomainEventBus<IEvent> eventBus;
    private CrudRepository<Message, UUID> messageRepository;
    private IUserRepository userRepository;
    private IMessageDeliveryService deliveryService;
    private Type[] events = new Type[] { DeliveryResponseReceivedEvent.class, DeliveryRequestResultReceivedEvent.class };

    public MessageApplicationService(IDomainEventBus<IEvent> eventBus,
                                     CrudRepository<Message, UUID> messageRepository,
                                     IUserRepository userRepository,
                                     IMessageDeliveryService deliveryService) {
        this.eventBus = eventBus;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.deliveryService = deliveryService;
        Subscribe(this.eventBus);
    }

    /**
     * Send message to recipient
     * @param command
     * @return
     */
    public Future<Message> send(MessageSendCommand command) {

        CompletableFuture<Message> result = new CompletableFuture<Message>();

        logger.debug("send [to=" + command.getToUserName() + ",content=" + command.getContent() +"]");

        User fromUser = userRepository.findByUserName(command.getFromUserName());
        User toUser = userRepository.findByUserName(command.getToUserName());

/*        MessageDeliveryRequest deliveryRequest = new MessageDeliveryRequest(fromUser, toUser, UUID.randomUUID(), command.getContent());
        CompletableFuture<DeliveryRequestResult> requestDeliveryFuture = deliveryService.requestDelivery(deliveryRequest);
        requestDeliveryFuture.exceptionally((exc, result) -> {
            handleDeliveryRequestResult(result);

        });*/

        return result;
    }

    private void handleDeliveryRequestResult(DeliveryRequestResult result) {
        this.eventBus.publish(new DeliveryRequestResultReceivedEvent(result));
    }

    /**
     * Adapters
     * @param deliveryResponse
     */

    public void handleDeliveryResponse(DeliveryResponse deliveryResponse) {
        this.eventBus.publish(new DeliveryResponseReceivedEvent(deliveryResponse));
    }

    private void Subscribe(IDomainEventBus<IEvent> eventBus) {
        eventBus
                .subscribe()
                .filter(e -> { return Arrays.stream(events).anyMatch(se -> se.getClass().equals(e.getClass())); })
                .subscribe(event -> HandleEvent(event));
    }

    private void HandleEvent(IEvent event) {
        logger.debug("OnEvent [" + event.toString() + "]");

        if (event instanceof DeliveryResponseReceivedEvent)
        {
            Message message = messageRepository.findOne(((DeliveryResponseReceivedEvent) event).getDeliveryResponse().getRequestId());
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
