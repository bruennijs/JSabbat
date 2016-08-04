package sabbat.apigateway.location.controller;


import com.sun.javafx.binding.StringFormatter;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sabbat.apigateway.location.command.ActivityCommandFactory;
import sabbat.apigateway.location.command.IActivityCommandFactory;
import sabbat.apigateway.location.command.ICommand;
import sabbat.apigateway.location.command.StartActivityCommand;
import sabbat.apigateway.location.controller.dto.MapMyTracksResponse;
import sabbat.apigateway.location.transformation.ActivityServiceTransformation;
import org.slf4j.Logger;
import sabbat.location.infrastructure.client.dto.IActivityResponseDto;


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

    private IActivityCommandFactory activityCommandFactory;
    private ApplicationContext applicationContext;

    public MapMyTracksApiController(IActivityCommandFactory activityCommandFactory) {
        this.activityCommandFactory = activityCommandFactory;
        this.applicationContext = applicationContext;
        logger.debug("constructor");
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

    public ResponseEntity<MapMyTracksResponse> startActivity(
                                @RequestParam(value= "request") String requestType,
                                @RequestParam(value= "activity_id", required = false) String activity_id,
                                @RequestParam(value= "title") String title,
                                @RequestParam(value= "points") String points,
                                @RequestParam(value= "source", required = false) String source,
                                @RequestParam(value = "tags", required = false) String tags)  throws Exception {


        logger.debug(StringFormatter.format("start activity [request=%1s, title=%2s, points=%3s]", requestType, title, points).getValue());

        try {

            ICommand command = activityCommandFactory.getCommand(requestType, title, points, source, activity_id);

            Future<? extends IActivityResponseDto> response = command.executeAsync();

            return new ResponseEntity(
                        transformation.transformResponse(response.get(START_ACTIVITY_RESPONSE_TIMEOUT, TimeUnit.MILLISECONDS)),
                        HttpStatus.OK);
        }
        catch(Exception exc)
        {
            logger.error("startActivity failed", exc);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

/*    @RequestMapping(
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
    }*/

    @RequestMapping(path = "/activities",
            method = RequestMethod.GET)
    public @ResponseBody String get() throws Exception {
        return "myactivity";
    }
}
