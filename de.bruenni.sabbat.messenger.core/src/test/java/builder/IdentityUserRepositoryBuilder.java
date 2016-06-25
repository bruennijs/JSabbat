package builder;

import sabbat.messenger.core.domain.identity.aggregates.User;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * Created by bruenni on 25.06.16.
 */
public class IdentityUserRepositoryBuilder {
    private String id = UUID.randomUUID().toString();
    private URI uri;
    private String name = "UserName";

    public IdentityUserRepositoryBuilder() {
        try {
            uri = new URI("email@me.com");
        }
        catch (URISyntaxException exc)
        {
        }
    }

    public User Build() {
        return new User(id, uri, name);
    }
}
