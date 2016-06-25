package builder;

import sabbat.messenger.core.domain.messenger.ValueObjects.User;
import sabbat.messenger.core.domain.messenger.aggregates.Message;

import java.util.Date;
import java.util.UUID;

/**
 * Created by bruenni on 08.06.16.
 */
public class MessageBuilder {
    private User from = new UserBuilder().Build();
    private User to = new UserBuilder().Build();
    private Date timestamp = new Date();
    private Date delivered = new Date();

    public MessageBuilder() {
    }

    public Message Build()
    {
        return new Message(UUID.randomUUID(), from, to, timestamp, delivered);
    }
}
