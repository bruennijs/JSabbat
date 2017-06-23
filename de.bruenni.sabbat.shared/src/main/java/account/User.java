package account;

import identity.GroupRef;
import identity.UserRef;
import jdk.nashorn.internal.runtime.URIUtils;

import java.net.URI;
import java.util.*;

/**
 * Created by bruenni on 15.10.16.
 */
public class User extends UserRef {
    String id;
    String name;
    String email;
    List<GroupRef> groups;
    private Map<String, Object> properties;

    /**
     * Constructor.
     * @param id
     * @param name
     * @param email
     * @param groups
     */
    public User(String id, String name, String email, List<GroupRef> groups) {
        super(id, name, groups);
        this.id = id;
        this.name = name;
        this.email = email;
        this.groups = groups;
        this.properties = new HashMap<>();
    }

    /**
     * Constructor
     * @param id
     * @param name
     * @param email
     * @param groups
     * @param properties
     */
    public User(String id, String name, String email, List<GroupRef> groups, Map<String, Object> properties) {
        super(id, name, groups);
        this.id = id;
        this.name = name;
        this.email = email;
        this.groups = groups;
        this.properties = properties;
    }

    public User(String id, String name) {
        this(id, name, "", Arrays.asList(), new HashMap<>());
    }

    public String getName() {
        return name;
    }

    public List<GroupRef> getGroupRefs() {
        return groups;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    /**
     * Gets all properties associated with a user.
     * Depends on application to parse the bound context domain
     * sepecific properties out of it.
     * @return
     */
    public Map<String, Object> getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return "User{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            "} " + super.toString();
    }
}
