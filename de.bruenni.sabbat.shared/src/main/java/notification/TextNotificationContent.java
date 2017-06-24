package notification;

/**
 * Created by bruenni on 24.06.17.
 */
public class TextNotificationContent extends NotificationContent {
    private String text;

    public TextNotificationContent(String text) {
        this.text = text;
    }

    /**
     * Text of the message.
     * @return
     */
    public String getText() {
        return text;
    }

    @Override
    public String asText() {
        return getText();
    }

    @Override
    public String toString() {
        return "TextNotificationContent{" +
            "text='" + text + '\'' +
            "} " + super.toString();
    }
}
