package domain.aggregates;

import domain.aggregates.identity.User;

import java.util.Date;

/**
 * Created by bruenni on 05.06.16.
 */
public class Message {
    User from;
    User to;
    Date timestamp;
    Date delivered;
    String id;

    /**
     * Constructor.
     * @param from
     * @param to
     * @param timestamp
     * @param delivered
     */
    public Message(User from, User to, Date timestamp, Date delivered) {
        this.from = from;
        this.to = to;
        this.timestamp = timestamp;
        this.delivered = delivered;
    }

    public String getId() {
        return id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Date getDelivered() {
        return delivered;
    }

    public User getTo() {
        return to;
    }

    public User getFrom() {
        return from;
    }
}
