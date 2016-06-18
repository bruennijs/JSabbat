package sabbat.messenger.core.domain.aggregates;

import infrastructure.common.event.IEvent;
import infrastructure.common.event.IEventHandler;
import infrastructure.persistence.Entity;
import infrastructure.services.delivery.DeliveryRequestResult;
import infrastructure.services.delivery.DeliveryResponse;
import sabbat.messenger.core.domain.aggregates.identity.User;
import sabbat.messenger.core.domain.events.DeliveryResponseReceivedEvent;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.UUID;

/**
 * State each message can be in.
 */
enum MessageState
{
    /**
     * Initial state.
     */
    New,
    /**
     * Send to delivery service and pending for state
     * delivered or failed to be sent
     */
    Pending,
    /**
     * Final state
     */
    Delivered,

    /**
     * Failed could not be sent
     */
    Failed
};

/*
enum MessageEvents {
    MessageDelivered
}
*/

//static Type[] events = new Type[] { MessageDeliveredEvent.class }

/**
 * Created by bruenni on 05.06.16.
 */
public class Message extends Entity<UUID> implements IEventHandler {//extends EnumStateMachineConfigurerAdapter<MessageState, MessageEvents> {
    User from;
    User to;
    Date timestamp;
    Date delivered;

    /**
     * Constructor.
     * @param from
     * @param to
     * @param timestamp
     * @param delivered
     */
    public Message(UUID id, User from, User to, Date timestamp, Date delivered) {
        super(id);
        this.from = from;
        this.to = to;
        this.timestamp = timestamp;
        this.delivered = delivered;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Date getDelivered() {
        return delivered;
    }

    public User getTo() {
        return to;
    }

    public User getFrom() {
        return from;
    }


    @Override
    public void OnEvent(IEvent iEvent) {
        DeliveryResponseReceivedEvent deliveryResponseReceivedEvent = iEvent instanceof DeliveryResponseReceivedEvent ? ((DeliveryResponseReceivedEvent) iEvent) : null;

        if (deliveryResponseReceivedEvent != null)
        {
            this.delivered = deliveryResponseReceivedEvent.getTimestamp();
        }
    }

    @Override
    public Type[] getSupportedEvents() {
        return new Type[0];
    }

    /**
     * A message delivery request has been acked.
     * @param result
     */
    public void onDeliveryRequestResult(DeliveryRequestResult result) {

    }

    public IEvent onDeliveryResponse(DeliveryResponse response) {

        return new DeliveryResponseReceivedEvent(response);
    }
}
