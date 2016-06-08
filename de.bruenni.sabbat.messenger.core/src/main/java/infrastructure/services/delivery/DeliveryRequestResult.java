package infrastructure.services.delivery;

/**
 * Created by bruenni on 08.06.16.
 */
public class DeliveryRequestResult {
    private boolean result;

    public DeliveryRequestResult(boolean result) {
        this.result = result;
    }

    public boolean getResult() {
        return result;
    }
}
