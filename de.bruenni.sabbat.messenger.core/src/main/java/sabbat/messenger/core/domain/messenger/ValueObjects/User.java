package sabbat.messenger.core.domain.messenger.ValueObjects;

import infrastructure.common.ValueObject;

/**
 * Created by bruenni on 25.06.16.
 * Value object of an user of domain identity
 */
public class User extends ValueObject {
    String id;
    String name;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the id
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the user name.
     * @return
     */
    public String getName() {
        return name;
    }
}
