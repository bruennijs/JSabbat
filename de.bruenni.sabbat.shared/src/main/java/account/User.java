package account;

import identity.GroupRef;
import identity.UserRef;
import jdk.nashorn.internal.runtime.URIUtils;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bruenni on 15.10.16.
 */
public class User extends UserRef {
    String id;
    String name;
    String email;
    List<GroupRef> groups;

    public User(String id, String name, String email, List<GroupRef> groups) {
        super(id, name, groups);
        this.id = id;
        this.name = name;
        this.email = email;
        this.groups = groups;
    }

    public User(String id, String name) {
        this(id, name, "", Arrays.asList());
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
}
