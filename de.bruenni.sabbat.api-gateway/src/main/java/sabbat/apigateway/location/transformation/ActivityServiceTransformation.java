package sabbat.apigateway.location.transformation;

import org.springframework.data.geo.Point;
import sabbat.apigateway.location.controller.dto.ActivityCreatedResponse;
import sabbat.apigateway.location.controller.dto.ActivityStoppedResponse;
import sabbat.location.infrastructure.client.dto.*;

/**
 * Created by bruenni on 24.07.16.
 */
public class ActivityServiceTransformation {
    /**
     * Transformatation of DTO to apigateway DTO
     * @param dto
     * @return
     */
    public ActivityCreatedResponse transformResponse(ActivityCreatedResponseDto dto)
    {
        return new ActivityCreatedResponse(dto.getId());
    }

    /**
     *
     * @param dto
     * @return
     */
    public ActivityStoppedResponse transformResponse(ActivityStoppedResponseDto dto)
    {
        return new ActivityStoppedResponse();
    }

    /***
     * Transforms controller request to IActivityService command.
     *
     * @param requestType
     * @param title
     * @return
     */
    public ActivityCreateCommandDto transformStartRequest(String requestType, String title, String points)
    {
        return new ActivityCreateCommandDto(title);
    }

    /***
     * Transforms controller request to IActivityService command.
     * @param requestType
     *
     * @return
     */
    public ActivityUpdateCommandDto transformUpdateRequest(String requestType, String id, String points)
    {
        return new ActivityUpdateCommandDto(id, new Point[0]);
    }

    public ActivityStopCommandDto transformStopRequest(String requestType, String activityId) {
        return new ActivityStopCommandDto(activityId);
    }
}
