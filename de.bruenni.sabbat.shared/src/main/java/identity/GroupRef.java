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
}
