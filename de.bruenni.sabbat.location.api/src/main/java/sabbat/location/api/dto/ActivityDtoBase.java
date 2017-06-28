package sabbat.location.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bruenni on 04.07.16.
 */
public abstract class ActivityDtoBase {

    @JsonProperty("id")
    private String id;

    public ActivityDtoBase() {
    }

    protected ActivityDtoBase(String id) {
        this.id = id;
    }

    /**
     * Gets the ID of the activity
     * @return
     */
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivityDtoBase that = (ActivityDtoBase) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ActivityDtoBase{" +
                "id='" + id + '\'' +
                '}';
    }
}
