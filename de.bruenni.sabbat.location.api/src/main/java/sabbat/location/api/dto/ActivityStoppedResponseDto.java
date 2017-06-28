package sabbat.location.api.dto;

/**
 * Created by bruenni on 24.07.16.
 */
public class ActivityStoppedResponseDto implements IActivityResponseDto {
    private String id;

    public ActivityStoppedResponseDto(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ActivityStoppedResponseDto{" +
                "id='" + id + '\'' +
                '}';
    }
}
