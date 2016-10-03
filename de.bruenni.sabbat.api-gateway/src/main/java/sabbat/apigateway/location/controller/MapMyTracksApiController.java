package sabbat.apigateway.location.controller;


import com.sun.javafx.binding.StringFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import sabbat.apigateway.location.command.IActivityCommandFactory;
import sabbat.apigateway.location.command.ICommand;
import sabbat.apigateway.location.controller.dto.MapMyTracksResponse;
import sabbat.apigateway.location.controller.converter.LocationApiDtoConverter;
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
    final Logger loggerTraffic = org.slf4j.LoggerFactory.getLogger("apigateway.traffic");

    private LocationApiDtoConverter transformation = new LocationApiDtoConverter();

    private IActivityCommandFactory activityCommandFactory;

    @Autowired
    @Qualifier("ActivityCommandFactory")
    public void setActivityCommandFactory(IActivityCommandFactory activityCommandFactory) {
        this.activityCommandFactory = activityCommandFactory;
    }

    /**
     * Constructor
     */
    public MapMyTracksApiController() {
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

    public ResponseEntity<MapMyTracksResponse> postMethod(
                                @RequestParam(value= "request") String requestType,
                                @RequestParam(value= "activity_id", required = false) String activity_id,
                                @RequestParam(value= "title", required = false) String title,
                                @RequestParam(value= "points", required = false) String points,
                                @RequestParam(value= "source", required = false) String source,
                                @RequestParam(value = "tags", required = false) String tags)  throws Exception {

        if (loggerTraffic.isDebugEnabled())
            loggerTraffic.debug(StringFormatter.format("--> [request=%1s, axtivity_id=%2s, title=%3s, points=%4s]", requestType, activity_id, title, points).getValue());

        try {

            ICommand command = activityCommandFactory.getCommand(requestType, title, points, source, activity_id);

            if (!command.getPublishOnly()) {
                Future<? extends IActivityResponseDto> future = command.requestAsync();

                MapMyTracksResponse response = transformation.transformResponse(future.get(START_ACTIVITY_RESPONSE_TIMEOUT, TimeUnit.MILLISECONDS));

                //ActivityCreatedResponse response = new ActivityCreatedResponse(new Date().getTime());
                if (loggerTraffic.isDebugEnabled())
                    loggerTraffic.debug(StringFormatter.format("<-- [%1s]", response).getValue());

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML.toString());

                return new ResponseEntity(
                        response,
                        headers,
                        HttpStatus.OK);
            }
            else
            {
                // publish only command
                command.publish();

                if (loggerTraffic.isDebugEnabled())
                    loggerTraffic.debug("<-- [200 OK]");

                return new ResponseEntity(HttpStatus.OK);
            }
        }
        catch(Exception exc)
        {
            logger.error("postMethod failed", exc);
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
