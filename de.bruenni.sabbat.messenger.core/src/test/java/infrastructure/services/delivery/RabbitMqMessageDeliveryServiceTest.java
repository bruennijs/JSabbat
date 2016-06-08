package infrastructure.services.delivery;

import builder.MessageBuilder;
import infrastructure.services.delivery.implementation.RabbitMqMessageDeliveryService;
import org.junit.Assert;
import org.junit.Test;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by bruenni on 08.06.16.
 */
public class RabbitMqMessageDeliveryServiceTest {
    @Test
    public void When_deliver_expect_future_completes() throws ExecutionException, InterruptedException, URISyntaxException {
        RabbitMqMessageDeliveryService sut = new RabbitMqMessageDeliveryService();
        Future<DeliveryRequestResult> future = sut.requestDelivery(new MessageBuilder().Build());
        Assert.assertEquals(false, future.get().getResult());
    }
}