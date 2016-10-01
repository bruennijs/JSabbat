package sabbat.location.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bruenni on 04.07.16.
 */
public class ActivityCreateRequestDto extends ActivityDtoBase {

    @JsonProperty("title")
    private String title;

    public ActivityCreateRequestDto() {
        super();
    }

    public ActivityCreateRequestDto(String id, String title) {
        super(id);
        this.title = title;
    }


    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ActivityCreateRequestDto that = (ActivityCreateRequestDto) o;

        return title.equals(that.title);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ActivityCreateRequestDto{" +
                "title='" + title + '\'' +
                "} " + super.toString();
    }
}
