package identity;

import java.util.List;

/**
 * Created by bruenni on 15.10.16.
 */
public class UserRef {
    String id;
    String name;
    List<GroupRef> groups;

    /**
     * Constructor
     * @param id
     * @param name
     * @param groups
     */
    public UserRef(String id, String name, List<GroupRef> groups) {
        this.id = id;
        this.name = name;
        this.groups = groups;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<GroupRef> getGroups() {
        return groups;
    }
}
