package sabbat.location.api.dto;

/**
 * Created by bruenni on 04.07.16.
 */
public class ActivityStopRequestDto extends ActivityRequestDtoBase {

    public ActivityStopRequestDto() {
    }

    /**
     * Constructor
     */
    public ActivityStopRequestDto(String id, String identityToken) {
        super(id, identityToken);
    }
}
