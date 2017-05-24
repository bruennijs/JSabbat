package sabbat.messenger.core.domain.messenger.aggregates;

import infrastructure.common.event.Event;
import infrastructure.common.gateway.AggregateCorrelationException;
import infrastructure.common.gateway.AsyncRequestBase;
import infrastructure.persistence.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sabbat.messenger.core.domain.events.DeliveryRequestResultReceived;
import sabbat.messenger.core.domain.messenger.ValueObjects.User;
import sabbat.messenger.core.domain.messenger.events.DeliveryResponseReceivedEvent;
import sabbat.messenger.core.domain.messenger.events.MessageDeliveredEvent;
import sabbat.messenger.core.infrastructure.delivery.DeliveryRequestResult;
import sabbat.messenger.core.infrastructure.delivery.DeliveryResponse;

import java.text.MessageFormat;
import java.util.Date;
import java.util.UUID;

/*
enum MessageEvents {
    MessageDelivered
}
*/

//static Type[] events = new Type[] { MessageDeliveredEvent.class }

/**
 * Created by bruenni on 05.06.16.
 */
public class Message extends Entity<UUID> implements IMessage {//extends EnumStateMachineConfigurerAdapter<MessageState, MessageEvents> {

    static final Logger logger = LogManager.getLogger(Message.class.getName());

    User from;
    User to;
    Date createdOn;
    Date deliveredOn;
    MessageState state;

    /**
     * Constructor.
     * @param from
     * @param to
     * @param timestamp
     * @param delivered
     */
    public Message(UUID id,
                   User from,
                   User to,
                   Date timestamp,
                   Date delivered) {
        super(id);
        this.from = from;
        this.to = to;
        this.createdOn = timestamp;
        this.deliveredOn = delivered;
        setState(MessageState.New);
    }

    @Override
    public Date getCreatedOn() {
        return createdOn;
    }

    @Override
    public Date getDeliveredOn() {
        return deliveredOn;
    }

    @Override
    public User getTo() {
        return to;
    }

    @Override
    public User getFrom() {
        return from;
    }

    @Override
    public MessageState getState() {
        return state;
    }

    private void setState(MessageState newState) {
        logger.debug(MessageFormat.format("Message state changed [{2}:{0}->{1}]", this.state, newState, getId()));
        this.state = newState;
    }

    /**
     * Handles domain events.
     * @param iEvent
     * @return new events created.
     */
    public Event OnEvent(Event iEvent) {
        DeliveryResponseReceivedEvent deliveryResponseReceivedEvent = iEvent instanceof DeliveryResponseReceivedEvent ? ((DeliveryResponseReceivedEvent) iEvent) : null;

        if (deliveryResponseReceivedEvent != null)
        {
            return HandleDeliveryResponseEvent(deliveryResponseReceivedEvent);
        }

        DeliveryRequestResultReceived messageStateChangedEvent = iEvent instanceof DeliveryRequestResultReceived ? ((DeliveryRequestResultReceived) iEvent) : null;
        if (messageStateChangedEvent != null)
        {
            return HandleDeliveryRequestResult();
        }

        return null;
    }

    private Event HandleDeliveryRequestResult() {
        if (this.getState() == MessageState.New)
            setState(MessageState.Pending);
        return null;
    }

    private Event HandleDeliveryResponseEvent(DeliveryResponseReceivedEvent deliveryResponseReceivedEvent) {
        if (deliveryResponseReceivedEvent.getDeliveryResponse().isDeliverySuccessful()) {
            this.deliveredOn = deliveryResponseReceivedEvent.getCreatedOn();
            setState(MessageState.Delivered);

            logger.debug("Message delivered {" + deliveryResponseReceivedEvent.getDeliveryResponse() + "}");

            return new MessageDeliveredEvent(UUID.randomUUID(),
                    this.getId(),
                    deliveryResponseReceivedEvent.getCreatedOn());
        }
        else
        {
            //this.state = MessageState.Failed;
            logger.info("Message delivery response failed [" + deliveryResponseReceivedEvent.getDeliveryResponse() + "]");
        }

        return null;
    }

    /**
     * A message delivery request has been acked.
     * @param result
     */
    public Event onDeliveryRequestResult(DeliveryRequestResult result) {

        //ValidateCorrelation(result);
        if (result.getResult())
            return new DeliveryRequestResultReceived(this.getId(), MessageState.Pending);
        else
            logger.debug("Delivery request result failure [{0}]", result);

        return null;
    }

    public Event onDeliveryResponse(DeliveryResponse response) {

        //ValidateCorrelation(response);

        return new DeliveryResponseReceivedEvent(this.getId(), response);
    }

    private void ValidateCorrelation(AsyncRequestBase<String> asyncRequestBase) throws AggregateCorrelationException {
        if (!UUID.fromString(asyncRequestBase.getCorrelationId()).equals(this.getId()))
            throw new AggregateCorrelationException(asyncRequestBase.getCorrelationId(), this.getId().toString(), "Message and correlation does not match");
    }
}
