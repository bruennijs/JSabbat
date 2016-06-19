package builder;

import sabbat.messenger.core.domain.aggregates.identity.User;

import java.net.URI;
import java.net.URISyntaxException;

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

        return new User(uri, "olli");
    }
}
