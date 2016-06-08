package builder;

import domain.aggregates.Message;
import domain.aggregates.identity.User;

import java.net.URISyntaxException;
import java.util.Date;

/**
 * Created by bruenni on 08.06.16.
 */
public class MessageBuilder {
    private User from = new UserBuilder().Build();
    private User to = new UserBuilder().Build();
    private Date timestamp = new Date();
    private Date delivered = new Date();

    public MessageBuilder() throws URISyntaxException {
    }

    public Message Build()
    {
        return new Message(from, to, timestamp, delivered);
    }
}
