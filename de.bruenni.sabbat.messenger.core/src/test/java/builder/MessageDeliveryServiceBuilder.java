package builder;

import sabbat.messenger.core.infrastructure.delivery.DeliveryRequest;
import sabbat.messenger.core.infrastructure.delivery.DeliveryRequestResult;
import sabbat.messenger.core.infrastructure.delivery.IMessageDeliveryService;

import java.util.concurrent.CompletableFuture;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by bruenni on 25.06.16.
 */
public class MessageDeliveryServiceBuilder {

    private final IMessageDeliveryService mock = mock(IMessageDeliveryService.class);

    public IMessageDeliveryService BuildMocked() {
        CompletableFuture<DeliveryRequestResult> future = new CompletableFuture<>();
        when(mock.requestDelivery(any())).then(invocation ->
        {
            DeliveryRequest request = (DeliveryRequest)invocation.getArgument(0);
            future.complete(new DeliveryRequestResult(request.getCorrelationId(), true));
            return future;
        });
        return mock;
    }
}
