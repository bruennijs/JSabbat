package sabbat.messenger.core.domain.events;

import infrastructure.common.event.implementation.Event;
import sabbat.messenger.core.domain.messenger.aggregates.MessageState;

import java.util.UUID;

/**
 * Created by bruenni on 25.06.16.
 */
public class DeliveryRequestResultReceived extends Event {
    public DeliveryRequestResultReceived(UUID id, MessageState newState) {
        super(id);
        this.newState = newState;
    }

        private MessageState newState;


        public MessageState getNewState() {
            return newState;
        }
}
