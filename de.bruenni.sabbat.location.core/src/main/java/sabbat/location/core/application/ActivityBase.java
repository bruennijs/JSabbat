package sabbat.location.core.application;

/**
 * Created by bruenni on 04.07.16.
 */
public abstract class ActivityBase {

    private String id;

    protected ActivityBase(String id) {
        this.id = id;
    }

    /**
     * Gets the ID of the activity
     * @return
     */
    public String getId() {
        return id;
    }
}
