package builder;

import sabbat.messenger.core.domain.messenger.ValueObjects.User;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * Created by bruenni on 08.06.16.
 */
public class UserBuilder {
    public User Build() {
        URI uri = null;
        try {
            uri = new URI("hello@test.de");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return new User(UUID.randomUUID().toString(), "olli");
    }
}
