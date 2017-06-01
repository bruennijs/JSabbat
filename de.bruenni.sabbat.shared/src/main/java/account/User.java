package account;

import identity.GroupRef;
import jdk.nashorn.internal.runtime.URIUtils;

import java.net.URI;

/**
 * Created by bruenni on 15.10.16.
 */
public class User {
    String id;
    String name;
    String email;
    GroupRef[] groups;

    public User(String id, String name, String email, GroupRef[] groups) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.groups = groups;
    }

    public User(String id, String name) {
        this(id, name, "", new GroupRef[0]);
    }

    public String getName() {
        return name;
    }

    public GroupRef[] getGroupRefs() {
        return groups;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }
}
