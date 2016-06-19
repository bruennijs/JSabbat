package domain.aggregates;

import builder.DeliveryResponseBuilder;
import builder.MessageBuilder;
import infrastructure.common.event.IEvent;
import org.junit.Test;
import org.springframework.util.Assert;
import sabbat.messenger.core.application.services.MessageSendCommand;
import sabbat.messenger.core.domain.aggregates.Message;
import sabbat.messenger.core.domain.aggregates.MessageState;
import sabbat.messenger.core.domain.events.DeliveryResponseReceivedEvent;
import sabbat.messenger.core.infrastructure.delivery.DeliveryResponse;

import java.net.URISyntaxException;

/**
 * Created by bruenni on 19.06.16.
 */
public class MessageTest {
    @Test
    public void When_message_handling_response_expect_MessageresponseReceivedEvent_returned() throws URISyntaxException {
        Message sut = new MessageBuilder().Build();
        IEvent event = sut.onDeliveryResponse(new DeliveryResponseBuilder().Build());

        Assert.isInstanceOf(DeliveryResponseReceivedEvent.class, event);
    }

    @Test
    public void When_handling_MessageresponseReceivedEvent_expect_state_transition_to_delivered()
    {
        Message sut = new MessageBuilder().Build();
        IEvent event = sut.onDeliveryResponse(new DeliveryResponseBuilder().Build());

        DeliveryResponseReceivedEvent receivedEvent = (DeliveryResponseReceivedEvent) event;
        sut.OnEvent(receivedEvent);
        org.junit.Assert.assertEquals(MessageState.Delivered, sut.getState());
    }

    @Test
    public void When_handling_MessageresponseReceivedEvent_expect_deliveredOn_set_to_event_timestamp()
    {
        Message sut = new MessageBuilder().Build();
        IEvent event = sut.onDeliveryResponse(new DeliveryResponseBuilder().Build());

        DeliveryResponseReceivedEvent receivedEvent = (DeliveryResponseReceivedEvent) event;
        sut.OnEvent(receivedEvent);
        org.junit.Assert.assertEquals(receivedEvent.getTimestamp(), sut.getDeliveredOn());
    }

    @Test
    public void When_response_contains_failure_expect_state_failed()
    {
        Message sut = new MessageBuilder().Build();

        org.junit.Assert.assertEquals(MessageState.New, sut.getState());

        sut.onDeliveryResponse(new DeliveryResponseBuilder().WithSuccess(false).Build());

        org.junit.Assert.assertEquals(MessageState.New, sut.getState());
    }
}
