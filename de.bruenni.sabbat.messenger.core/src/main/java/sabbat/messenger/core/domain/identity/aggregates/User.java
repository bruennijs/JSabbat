package sabbat.messenger.core.domain.identity.aggregates;

import infrastructure.persistence.Entity;

import java.net.URI;

/**
 * Created by bruenni on 05.06.16.
 */
public class User extends Entity<String> {
    private String name;
    private URI email;

    /**
     * Constructor
     * @param email
     * @param name
     */
    public User(String id, URI email, String name) {
        super(id);
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

    /**
     * Crates value object of aggregate of identity domain.
     * @return
     */
    public sabbat.messenger.core.domain.messenger.ValueObjects.User toUserValueObject()
    {
        return new sabbat.messenger.core.domain.messenger.ValueObjects.User(getId(), getName());
    }
}
