package sabbat.location.infrastructure.client;

import sabbat.location.infrastructure.client.dto.ActivityRequestDtoBase;

/**
 * Created by bruenni on 09.10.16.
 */
public class Confirmation<T extends Object> {
    private T messageObject;
    boolean acked;
    private String cause;

    public Confirmation(T messageObject, boolean acked, String cause) {
        this.messageObject = messageObject;
        this.acked = acked;
        this.cause = cause;
    }

    /**
     * true if message has been sent
     * @return
     */
    public boolean isAcked() {
        return acked;
    }

    public T getMessageObject() {
        return messageObject;
    }

    @Override
    public String toString() {
        return "Confirmation{" +
          "messageObject=" + messageObject +
          ", acked=" + acked +
          ", cause='" + cause + '\'' +
          '}';
    }
}
