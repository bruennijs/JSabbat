package notification;

import java.util.List;

/**
 * Created by bruenni on 24.06.17.
 */
public interface EmailSender {
    /**
     * Sends text message synchronously email to recipient.
     * @param toAddresses
     * @param subject
     * @param text
     */
    void sendText(List<String> toAddresses, String subject, String text) throws Exception;
}
