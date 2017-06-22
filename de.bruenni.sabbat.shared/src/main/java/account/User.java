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
    private Set<Map.Entry<String, Object>> properties;


    public User(String id, String name, String email, List<GroupRef> groups) {
        super(id, name, groups);
        this.id = id;
        this.name = name;
        this.email = email;
        this.groups = groups;
        this.properties = new HashSet<>();
    }

    public User(String id, String name, String email, List<GroupRef> groups, Set<Map.Entry<String, Object>> properties) {
        super(id, name, groups);
        this.id = id;
        this.name = name;
        this.email = email;
        this.groups = groups;
        this.properties = properties;
    }

    public User(String id, String name) {
        this(id, name, "", Arrays.asList(), new HashSet<>());
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
    public Set<Map.Entry<String, Object>> getProperties() {
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
