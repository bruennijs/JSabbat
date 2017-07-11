package identity;

import java.util.List;

/**
 * Created by bruenni on 15.10.16.
 */
public class UserRef {
    String id;
    List<GroupRef> groups;

    /**
     * Constructor
     * @param id Natural identifier of the user.
     * @param groups
     */
    public UserRef(String id, List<GroupRef> groups) {
        this.id = id;
        this.groups = groups;
    }

    /**
     * Natural identifier of the user.
     * @return
     */
    public String getId() {
        return id;
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
