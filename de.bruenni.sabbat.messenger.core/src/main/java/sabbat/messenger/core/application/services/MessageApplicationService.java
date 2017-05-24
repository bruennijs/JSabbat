package sabbat.messenger.core.application.services;

import com.sun.javafx.binding.StringFormatter;
import infrastructure.common.gateway.AggregateCorrelationException;
import sabbat.messenger.core.domain.messenger.aggregates.IMessage;
import sabbat.messenger.core.infrastructure.delivery.DeliveryRequestResult;
import sabbat.messenger.core.infrastructure.delivery.DeliveryResponse;
import sabbat.messenger.core.infrastructure.delivery.IMessageDeliveryService;
import sabbat.messenger.core.infrastructure.delivery.MessageDeliveryRequest;
import sabbat.messenger.core.infrastructure.persistence.IMessageRepository;
import sabbat.messenger.core.infrastructure.persistence.IUserRepository;
import sabbat.messenger.core.domain.messenger.aggregates.Message;
import sabbat.messenger.core.domain.identity.aggregates.User;
import infrastructure.common.event.*;
import org.springframework.data.repository.CrudRepository;
import org.apache.logging.log4j.*;
import sabbat.messenger.core.domain.messenger.events.DeliveryRequestResultReceivedEvent;
import sabbat.messenger.core.domain.messenger.events.DeliveryResponseReceivedEvent;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Created by bruenni on 28.05.16.
 */
public class MessageApplicationService implements IEventHandler<Event> {
    static final Logger logger = LogManager.getLogger(MessageApplicationService.class.getName());

    private IDomainEventPublisher domainEventPublisher;
    //private IDomainEventBus<IEvent> eventBus;
    private IMessageRepository messageRepository;
    private IUserRepository userRepository;
    private IMessageDeliveryService deliveryService;
    private Type[] events = new Type[]
            {
                    DeliveryResponseReceivedEvent.class,
                    DeliveryRequestResultReceivedEvent.class
            };

    public MessageApplicationService(IDomainEventPublisher domainEventPublisher,
                                     IMessageRepository messageRepository,
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

        Message message = new Message(UUID.randomUUID(),
                sender.toUserValueObject(),
                recepient.toUserValueObject(),
                new Date(),
                null);

        CompletableFuture<DeliveryRequestResult> requestDeliveryFuture = deliverMessage(message);
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
        UUID msgId = UUID.fromString(deliveryResponse.getCorrelationId());
        Message message = messageRepository.findOne(msgId);

        if (message != null) {
            Event eventToPublish = message.onDeliveryResponse(deliveryResponse);

            this.domainEventPublisher.publish(eventToPublish);
        }
        else
            logger.debug(MessageFormat.format("Cannot find message by id [{0}]", msgId));
    }

    @Override
    public void OnEvent(Event event)
    {
        logger.debug("OnEvent IEventHandler [" + event.toString() + "]");

        UUID msgId = UUID.fromString(event.getAggregate().toString());
        Message message = messageRepository.findOne(msgId);
        if (message != null) {
            Event newEvent = message.OnEvent(event);
            this.domainEventPublisher.publish(newEvent);
        }
        else
            logger.debug(MessageFormat.format("Cannot find message by id [{0}]", msgId));
    }

    @Override
    public Type[] getSupportedEvents() {
        return events;
    }

    private CompletableFuture<DeliveryRequestResult> deliverMessage(Message message) {
        MessageDeliveryRequest deliveryRequest = new MessageDeliveryRequest(message, message.getId().toString());

        // call delievry service to send message
        return deliveryService.requestDelivery(deliveryRequest);
    }

    private void Subscribe(IDomainEventBus eventBus) {
        eventBus
                .subscribe()
                .filter(e -> { return Arrays.stream(events).anyMatch(se -> se.getClass().equals(e.getClass())); })
                .subscribe(event -> OnEvent(event));
    }
}
