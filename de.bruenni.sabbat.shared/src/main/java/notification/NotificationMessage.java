package notification;

import account.User;
import identity.UserRef;

import java.util.UUID;

/**
 * Created by bruenni on 24.06.17.
 */
public class NotificationMessage<TContent extends NotificationContent> {
    private UUID id;
    private User to;
    private TContent content;
    private String topic;

    /**
     * Constructor
     * @param to
     * @param content
     */
    public NotificationMessage(User to, String topic, TContent content) {
        this.topic = topic;
        this.id = UUID.randomUUID();
        this.content = content;
        this.to = to;
    }


        /**
         * Constructor
         * @param id
         * @param to
         * @param content
         */
    public NotificationMessage(UUID id, User to, String topic, TContent content) {
        this.id = id;
        this.to = to;
        this.topic = topic;
        this.content = content;
    }

    /**
     * User to whcih the message is sent to
     * @return
     */
    public User getTo() {
        return to;
    }

    /**
     * Gets the content of the notification
     * @return
     */
    public TContent getContent() {
        return content;
    }

    /**
     * Gets the topic (e.g. if implementation is email this can be the subject field)
     * @return
     */
    public String getTopic() {
        return topic;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "NotificationMessage{" +
            "id=" + id +
            ", to=" + to +
            ", content=" + content +
            ", topic='" + topic + '\'' +
            '}';
    }
}
