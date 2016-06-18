package infrastructure.services.delivery;

import infrastructure.common.gateway.RequestResult;

import java.util.UUID;

/**
 * Created by bruenni on 08.06.16.
 */
public class DeliveryRequestResult extends RequestResult<String, Boolean>{

    public DeliveryRequestResult(String correlationId, Boolean result)
    {
        super(correlationId, result);
    }
}
