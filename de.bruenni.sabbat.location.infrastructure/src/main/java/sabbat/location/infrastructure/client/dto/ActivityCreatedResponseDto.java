package sabbat.location.infrastructure.client.dto;

/**
 * Created by bruenni on 16.07.16.
 */
public class ActivityCreatedResponseDto extends ActivityDtoBase implements IActivityResponseDto {

    /**
     * Used in json deserializer.
     */
    public ActivityCreatedResponseDto() {
    }

    public ActivityCreatedResponseDto(String id)
    {
        super(id);
    }

    @Override
    public String toString() {
        return "ActivityCreatedResponseDto{" +
                "id='" + getId() + '\'' +
                '}';
    }
}
