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
     * @param id Natural identifier of the user.
     * @param name
     * @param groups
     */
    public UserRef(String id, String name, List<GroupRef> groups) {
        this.id = id;
        this.name = name;
        this.groups = groups;
    }

    /**
     * Natural identifier of the user.
     * @return
     */
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<GroupRef> getGroupRefs() {
        return groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRef userRef = (UserRef) o;

        return id.equals(userRef.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
