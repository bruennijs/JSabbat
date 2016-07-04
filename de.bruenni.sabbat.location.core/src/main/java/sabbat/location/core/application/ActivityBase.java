package sabbat.location.core.application;

/**
 * Created by bruenni on 04.07.16.
 */
public abstract class ActivityBase {

    private String id;

    protected ActivityBase(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
