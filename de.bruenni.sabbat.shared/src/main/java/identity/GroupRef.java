package identity;

/**
 * Created by bruenni on 15.10.16.
 */
public class GroupRef {
    /**
     * Natural identifier of the group.
     */
    String id;

    /**
     * Constructor
     * @param id
     */
    public GroupRef(String id) {
        this.id = id;
    }

    /**
     * Natural identifier of the group.
     */
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupRef groupRef = (GroupRef) o;

        return id.equals(groupRef.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
