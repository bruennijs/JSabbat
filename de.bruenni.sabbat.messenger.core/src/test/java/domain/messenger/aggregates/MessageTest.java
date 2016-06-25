package domain.messenger.aggregates;

import builder.DeliveryResponseBuilder;
import builder.MessageBuilder;
import infrastructure.common.event.IEvent;
import infrastructure.common.gateway.AggregateCorrelationException;
import org.junit.Test;
import org.springframework.util.Assert;
import sabbat.messenger.core.domain.messenger.aggregates.Message;
import sabbat.messenger.core.domain.messenger.aggregates.MessageState;
import sabbat.messenger.core.domain.messenger.events.DeliveryResponseReceivedEvent;
import sabbat.messenger.core.infrastructure.delivery.DeliveryRequestResult;

import java.net.URISyntaxException;

/**
 * Created by bruenni on 19.06.16.
 */
public class MessageTest {
    @Test
    public void When_message_handling_response_expect_MessageresponseReceivedEvent_returned() throws URISyntaxException, AggregateCorrelationException {
        Message sut = new MessageBuilder().Build();
        IEvent event = sut.onDeliveryResponse(new DeliveryResponseBuilder().Build());

        Assert.isInstanceOf(DeliveryResponseReceivedEvent.class, event);
    }

    @Test
    public void When_handling_MessageresponseReceivedEvent_expect_state_transition_to_delivered() throws AggregateCorrelationException {
        Message sut = new MessageBuilder().Build();
        IEvent event = sut.onDeliveryResponse(new DeliveryResponseBuilder().Build());
        sut.OnEvent(event);
        org.junit.Assert.assertEquals(MessageState.Delivered, sut.getState());
        sut.OnEvent(event);
        org.junit.Assert.assertEquals(MessageState.Delivered, sut.getState());
    }

    @Test
    public void When_handling_MessageresponseReceivedEvent_expect_no_state_state_transition_to_delivered_with_response_failure() throws AggregateCorrelationException {
        Message sut = new MessageBuilder().Build();
        IEvent event = sut.onDeliveryResponse(new DeliveryResponseBuilder().WithSuccess(false).Build());
        sut.OnEvent(event);
        org.junit.Assert.assertEquals(MessageState.New, sut.getState());
        IEvent event2 = sut.onDeliveryResponse(new DeliveryResponseBuilder().WithSuccess(true).Build());
        sut.OnEvent(event2);
        org.junit.Assert.assertEquals(MessageState.Delivered, sut.getState());
    }

    @Test
    public void When_handling_resuestreponse_and_DeliveryRequestResponse_expect_delivered_state() {
        Message sut = new MessageBuilder().Build();
        IEvent event1 = sut.onDeliveryRequestResult(new DeliveryRequestResult("notchecked", true));
        sut.OnEvent(event1);
        org.junit.Assert.assertEquals(MessageState.Pending, sut.getState());
        IEvent event2 = sut.onDeliveryResponse(new DeliveryResponseBuilder().WithSuccess(true).Build());
        sut.OnEvent(event2);
        org.junit.Assert.assertEquals(MessageState.Delivered, sut.getState());
    }


    @Test
    public void When_handling_DeliveryResponse_with_failure_expect_pending_state() {
        Message sut = new MessageBuilder().Build();
        IEvent event1 = sut.onDeliveryRequestResult(new DeliveryRequestResult("notchecked", true));
        sut.OnEvent(event1);
        org.junit.Assert.assertEquals(MessageState.Pending, sut.getState());
        IEvent event2 = sut.onDeliveryResponse(new DeliveryResponseBuilder().WithSuccess(false).Build());
        sut.OnEvent(event2);
        org.junit.Assert.assertEquals(MessageState.Pending, sut.getState());
    }

    @Test
    public void When_handling_MessageresponseReceivedEvent_expect_deliveredOn_set_to_event_timestamp() throws AggregateCorrelationException {
        Message sut = new MessageBuilder().Build();
        IEvent event = sut.onDeliveryResponse(new DeliveryResponseBuilder().Build());

        sut.OnEvent(event);
        org.junit.Assert.assertEquals(event.getTimestamp(), sut.getDeliveredOn());
    }

    @Test
    public void When_response_contains_failure_expect_state_failed() throws AggregateCorrelationException {
        Message sut = new MessageBuilder().Build();

        org.junit.Assert.assertEquals(MessageState.New, sut.getState());

        sut.onDeliveryResponse(new DeliveryResponseBuilder().WithSuccess(false).Build());

        org.junit.Assert.assertEquals(MessageState.New, sut.getState());
    }
}
