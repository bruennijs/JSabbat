package sabbat.apigateway.location.transformation;

import com.sun.javafx.binding.StringFormatter;
import org.springframework.data.geo.Point;
import sabbat.apigateway.location.controller.dto.ActivityCreatedResponse;
import sabbat.apigateway.location.controller.dto.ActivityStoppedResponse;
import sabbat.apigateway.location.controller.dto.MapMyTracksResponse;
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
    public MapMyTracksResponse transformResponse(IActivityResponseDto dto) throws Exception {
        if (dto instanceof ActivityCreatedResponseDto) {
            return new ActivityCreatedResponse(((ActivityCreatedResponseDto)dto).getId());
        }
        else if (dto instanceof ActivityStoppedResponseDto)
        {
            return new ActivityStoppedResponse();
        }

        throw new Exception(StringFormatter.format("IActivityResponseDto impl not supported [type=%1s]", dto.getClass().toString()).getValue());
    }



    /***
     * Transforms controller request to IActivityService command.
     * @param requestType
     *
     * @return
     */
    public ActivityUpdateRequestDto transformUpdateRequest(String requestType, String id, String points)
    {
        return new ActivityUpdateRequestDto(id, new Point[0]);
    }

    public ActivityStopRequestDto transformStopRequest(String requestType, String activityId) {
        return new ActivityStopRequestDto(activityId);
    }
}
