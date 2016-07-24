package sabbat.apigateway.location.controller;


import com.sun.javafx.binding.StringFormatter;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sabbat.apigateway.location.controller.dto.ActivityCreatedResponse;
import sabbat.apigateway.location.controller.dto.ActivityStoppedResponse;
import sabbat.apigateway.location.controller.dto.ActivityUpdatedResponse;
import sabbat.apigateway.location.controller.dto.MapMyTracksResponse;
import sabbat.apigateway.location.transformation.ActivityServiceTransformation;
import sabbat.location.core.application.ActivityUpdateCommand;
import sabbat.location.infrastructure.client.IActivityRemoteService;
import sabbat.location.infrastructure.client.dto.ActivityCreateCommandDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sabbat.location.infrastructure.client.dto.ActivityCreatedResponseDto;


import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by bruenni on 03.07.16.
 */
@Controller
//@ResponseBody
@RequestMapping(path = "/location/api/v1")
public class MapMyTracksApiController {

    private static final long START_ACTIVITY_RESPONSE_TIMEOUT = 5000;
    private static final long UPDATE_ACTIVITY_RESPONSE_TIMEOUT = 5000;


    final Logger logger = org.slf4j.LoggerFactory.getLogger(MapMyTracksApiController.class);
    final Logger loggerTraffic = org.slf4j.LoggerFactory.getLogger("location.traffic");

    private ActivityServiceTransformation transformation = new ActivityServiceTransformation();

    private IActivityRemoteService activityService;

    public MapMyTracksApiController(IActivityRemoteService activityService) {
        logger.debug("constructor");
        this.activityService = activityService;
    }

    /**
     * Content-Length: 173
     * Content-Type: application/x-www-form-urlencoded; charset=ISO-8859-1
     * Host: 192.168.2.108:8081
     * Connection: Keep-Alive
     * User-Agent: Apache-HttpClient/UNAVAILABLE (java 1.5)
     * Accept-Encoding: gzip,deflate

     * privicity=public&source=OruxMaps&title=2016-07-09+17%3A2120160709_1721&request=start_activity&version=60507&points=53.15081476+8.22890126+18.044+1468077604+&activity=cycling]
     * @param requestType
     * @param title
     * @param tags
     * @return
     * @throws Exception
     */
    @RequestMapping(
            path = "",
            method = RequestMethod.POST,
            produces = "application/xml",
            consumes = "application/x-www-form-urlencoded")

    public ResponseEntity<ActivityCreatedResponse> startActivity(
                                @RequestParam(value = "request") String requestType,
                                @RequestParam(value=  "title", required = false) String title,
                                @RequestParam(value=  "points") String points,
                                @RequestParam(value = "tags", required = false) String tags)  throws Exception {


        //logger.info(StringFormatter.format("startActivity [requestType=%1s, title=%2s]", requestType, title).getValue());

        try {
            if (requestType.equals("start_activity") || requestType.length() == 0) {

                Future<ActivityCreatedResponseDto> startResponse = this.activityService.start(transformation.transformStartRequest(requestType, title, points));

                return new ResponseEntity(
                        transformation.transformResponse(startResponse.get(START_ACTIVITY_RESPONSE_TIMEOUT, TimeUnit.MILLISECONDS)),
                        HttpStatus.OK);
            }
            else
            {
                logger.info("cannot handle request_type");
                return new ResponseEntity(HttpStatus.OK);
            }
        }
        catch(Exception exc)
        {
            logger.error("startActivity failed", exc);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(
            path = "",
            method = RequestMethod.POST,
            produces = "application/xml")
    public ResponseEntity<MapMyTracksResponse> updateActivity(@RequestParam(value = "request") String requestType,
                                           @RequestParam(value = "activity_id") String activityId,
                                           @RequestParam(value ="points") String points) throws InterruptedException {
        //logger.debug(StringFormatter.format("[requesType=%1s,activity_id=%2s,points=%3s]", requestType, activityId, points));

        try
        {
            Future<Void> future = this.activityService.update(transformation.transformUpdateRequest(requestType, activityId, points));

            future.wait(UPDATE_ACTIVITY_RESPONSE_TIMEOUT);

            return new ResponseEntity(new ActivityUpdatedResponse(), HttpStatus.OK);
        }
        catch(Exception exc)
        {
            logger.error("updateActivity failed", exc);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(
            path = "{activity_id}",
            method = RequestMethod.POST,
            produces = "application/xml")
    public ResponseEntity<MapMyTracksResponse> stopActivity(@PathVariable(value = "activity_id") String activityId,
                                                            @RequestParam(value = "request") String requestType) throws InterruptedException {
        //logger.debug(StringFormatter.format("[requesType=%1s,activity_id=%2s]", requestType, activityId).getValue());
        try
        {
            //this.activityService.update(new ActivityUpdateCommand(activityId, new Point[0], null, null));
            Future<Void> future = this.activityService.stop(transformation.transformStopRequest(requestType, activityId));

            future.wait(UPDATE_ACTIVITY_RESPONSE_TIMEOUT);

            return new ResponseEntity(new ActivityStoppedResponse(), HttpStatus.OK);
        }
        catch(Exception exc)
        {
            logger.error("stopActivity failed", exc);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/activities",
            method = RequestMethod.GET)
    public @ResponseBody String get() throws Exception {
        return "myactivity";
    }
}
