package builder;

import domain.aggregates.identity.User;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by bruenni on 08.06.16.
 */
public class UserBuilder {
    public User Build() throws URISyntaxException {
        return new User(new URI("hello@test.de"), "olli");
    }
}
