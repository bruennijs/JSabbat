package sabbat.location.infrastructure.client.dto;

/**
 * Created by bruenni on 16.07.16.
 */
public class ActivityCreatedResponseDto {
    private String id;

    protected ActivityCreatedResponseDto(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
