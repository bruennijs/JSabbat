package sabbat.location.infrastructure.client.dto;

/**
 * Created by bruenni on 16.07.16.
 */
public class ActivityCreatedResponseDto implements IActivityResponseDto {
    private String id;

    public ActivityCreatedResponseDto(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
