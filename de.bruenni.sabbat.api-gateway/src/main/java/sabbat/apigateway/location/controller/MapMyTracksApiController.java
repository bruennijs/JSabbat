package sabbat.apigateway.location.controller;


import com.sun.javafx.binding.StringFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sabbat.apigateway.location.controller.dto.ActivityCreatedResponse;
import sabbat.apigateway.location.controller.dto.ActivityStoppedResponse;
import sabbat.apigateway.location.controller.dto.ActivityUpdatedResponse;
import sabbat.location.core.application.ActivityCreateCommand;
import sabbat.location.core.application.ActivityUpdateCommand;
import sabbat.location.core.application.IActivityApplicationService;

import java.util.Map;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by bruenni on 03.07.16.
 */
@Controller
// @ConfigurationProperties(prefix="application.mapmytracks.")
//@Configuratio
//@ResponseBody
@RequestMapping(path = "/location/api/v1")
public class MapMyTracksApiController {

    private static Logger logger = LogManager.getLogger(MapMyTracksApiController.class);
    private IActivityApplicationService activityService;

    public MapMyTracksApiController(IActivityApplicationService activityService) {
        logger.debug("constructor");
        this.activityService = activityService;
    }

    @Value("${application.mapmytracks.text}")
    public String text;

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
                                @RequestParam(value = "activity_id", required = false) String activityIdParameter,
                                @RequestParam(value=  "title", required = false) String title,
                                @RequestParam(value = "tags", required = false) String[] tags)  throws Exception {

        logger.info("REQUEST_TYPE=" + requestType);
        //StringFormatter.format("startActivity [requestType=%1s, title=%2s]", requestType, title)

        if (requestType.equals("start_activity")) {
            String activityId = UUID.randomUUID().toString();

            this.activityService.start(new ActivityCreateCommand(activityId, title));

            return new ResponseEntity(new ActivityCreatedResponse(activityId), HttpStatus.OK);
/*            return StringFormatter.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<message>\n" +
                    "    <type>activity_started</type>\n" +
                    "    <activity_id>%1s</activity_id>\n" +
                    "</message>", activityId).toString();*/
        }
        else
        {
            logger.debug("cannot handle request_type");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    /*
    @RequestMapping(
            path = "",
            method = RequestMethod.POST,
            produces = "application/xml")
    public @ResponseBody ActivityUpdatedResponse updateActivity(@RequestParam(value = "request") String requestType,
                                                  @RequestParam(value = "activity_id") String activityId,
                                                  @RequestParam(value ="points", required = false) String points)
    {
        logger.debug(StringFormatter.format("[requesType=%1s,activity_id=%2s,points=%3s]", requestType, activityId, points));

        this.activityService.update(new ActivityUpdateCommand(activityId, new Point[0], null, null));

        return new ActivityUpdatedResponse();
    }
    */


    @RequestMapping(
            path = "{activity_id}",
            method = RequestMethod.POST,
            produces = "application/xml")
    public ResponseEntity<ActivityStoppedResponse> stopActivity(@PathVariable(value = "activity_id") String activityId,
                                                               @RequestParam(value = "request") String requestType)
    {
        logger.debug(StringFormatter.format("[requesType=%1s,activity_id=%2s]", requestType, activityId));

        this.activityService.update(new ActivityUpdateCommand(activityId, new Point[0], null, null));

        return new ResponseEntity(new ActivityStoppedResponse(), HttpStatus.OK);
    }

    @RequestMapping(path = "/activities",
            method = RequestMethod.GET)
    public @ResponseBody String get() throws Exception {
        return "myactivity";
    }
}
