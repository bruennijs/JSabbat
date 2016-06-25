package sabbat.messenger.core.domain.messenger.aggregates;

import infrastructure.common.event.IEvent;
import infrastructure.persistence.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sabbat.messenger.core.domain.messenger.ValueObjects.User;
import sabbat.messenger.core.domain.messenger.events.DeliveryResponseReceivedEvent;
import sabbat.messenger.core.domain.messenger.events.MessageDeliveredEvent;
import sabbat.messenger.core.infrastructure.delivery.DeliveryRequestResult;
import sabbat.messenger.core.infrastructure.delivery.DeliveryResponse;

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
        this.state = MessageState.New;
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

    /**
     * Handles domain events.
     * @param iEvent
     * @return new events created.
     */
    public IEvent OnEvent(IEvent iEvent) {
        DeliveryResponseReceivedEvent deliveryResponseReceivedEvent = iEvent instanceof DeliveryResponseReceivedEvent ? ((DeliveryResponseReceivedEvent) iEvent) : null;

        if (deliveryResponseReceivedEvent != null)
        {
            if (deliveryResponseReceivedEvent.getDeliveryResponse().isDeliverySuccessful()) {
                this.deliveredOn = deliveryResponseReceivedEvent.getTimestamp();
                this.state = MessageState.Delivered;

                logger.debug("Message delivered {" + deliveryResponseReceivedEvent.getDeliveryResponse() + "}");

                return new MessageDeliveredEvent(UUID.randomUUID(),
                        this.getId(),
                        deliveryResponseReceivedEvent.getTimestamp());
            }
            else
            {
                //this.state = MessageState.Failed;
                logger.info("Message delivery response failed [" + deliveryResponseReceivedEvent.getDeliveryResponse() + "]");
            }
        }

        return null;
    }

    /**
     * A message delivery request has been acked.
     * @param result
     */
    public void onDeliveryRequestResult(DeliveryRequestResult result) {
    }

    public IEvent onDeliveryResponse(DeliveryResponse response)
    {
        return new DeliveryResponseReceivedEvent(this.getId(), response);
    }
}
