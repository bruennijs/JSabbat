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
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Created by bruenni on 28.05.16.
 */
public class MessageApplicationService implements IEventHandler<IEvent> {
    static final Logger logger = LogManager.getLogger(MessageApplicationService.class.getName());

    private IDomainEventPublisher domainEventPublisher;
    //private IDomainEventBus<IEvent> eventBus;
    private CrudRepository<Message, UUID> messageRepository;
    private IUserRepository userRepository;
    private IMessageDeliveryService deliveryService;
    private Type[] events = new Type[]
            {
                    DeliveryResponseReceivedEvent.class,
                    DeliveryRequestResultReceivedEvent.class
            };

    public MessageApplicationService(IDomainEventPublisher domainEventPublisher,
                                        CrudRepository<Message, UUID> messageRepository,
                                     IUserRepository userRepository,
                                     IMessageDeliveryService deliveryService) {
        this.domainEventPublisher = domainEventPublisher;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.deliveryService = deliveryService;
    }

    /**
     * Send message to recipient
     * @param command
     * @return complates aftre message has been sent or sending has been rejected
     */
    public CompletableFuture<Message> send(MessageSendCommand command) {

        CompletableFuture<Message> result = new CompletableFuture<Message>();

        logger.debug("send [to=" + command.getToUserName() + ", content=" + command.getContent() +"]");

        User sender = userRepository.findByUserName(command.getFromUserName());
        User recepient = userRepository.findByUserName(command.getToUserName());

        Message message = new Message(UUID.randomUUID(), sender, recepient, new Date(), null);

        MessageDeliveryRequest deliveryRequest = new MessageDeliveryRequest(sender,
                recepient,
                message.getId().toString(),
                command.getContent());

        // call delievry service to send message
        CompletableFuture<DeliveryRequestResult> requestDeliveryFuture = deliveryService.requestDelivery(deliveryRequest);
        requestDeliveryFuture.thenAccept(val -> {
            // value received
            message.onDeliveryRequestResult(val);

            result.complete(message);
        });

        requestDeliveryFuture.exceptionally(exc -> {

            result.completeExceptionally(exc);
            return null;
        });

        return result;
    }

    /**
     * Called by adapters to dispatch responses to message aggragates
     * @param deliveryResponse
     */

    public void handleDeliveryResponse(DeliveryResponse deliveryResponse) {

        // find message by id
        Message message = messageRepository.findOne(UUID.fromString(deliveryResponse.getCorrelationId()));

        this.domainEventPublisher.publish(message.onDeliveryResponse(deliveryResponse));
    }

    private void Subscribe(IDomainEventBus eventBus) {
        eventBus
                .subscribe()
                .filter(e -> { return Arrays.stream(events).anyMatch(se -> se.getClass().equals(e.getClass())); })
                .subscribe(event -> OnEvent(event));
    }

    @Override
    public void OnEvent(IEvent event)
    {
        logger.debug("OnEvent IEventHandler [" + event.toString() + "]");

        if (event instanceof DeliveryResponseReceivedEvent)
        {
            UUID uuid = UUID.fromString(((DeliveryResponseReceivedEvent) event).getDeliveryResponse().getCorrelationId());
            Message message = messageRepository.findOne(uuid);
            message.OnEvent(event);
        }
    }

    @Override
    public Type[] getSupportedEvents() {
        return events;
    }
}
