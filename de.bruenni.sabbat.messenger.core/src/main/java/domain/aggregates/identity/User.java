package domain.aggregates.identity;

import java.net.URI;

/**
 * Created by bruenni on 05.06.16.
 */
public class User {
    private String name;
    private URI email;

    /**
     * Constructor
     * @param email
     * @param name
     */
    public User(URI email, String name) {
        this.email = email;
        this.name = name;
    }

    /**
     * Gets the name of the user
     * @return
     */
    String getName() {return this.name;}

    /**
     * Gets the email address.
     * @return
     */
    URI getEmail() { return this.email;}
}
