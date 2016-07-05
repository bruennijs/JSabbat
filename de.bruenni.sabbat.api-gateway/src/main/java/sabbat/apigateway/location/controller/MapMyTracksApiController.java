package sabbat.apigateway.location.controller;


import com.sun.javafx.binding.StringFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sabbat.apigateway.location.controller.dto.MapMyTracksResponse;
import sabbat.apigateway.location.controller.dto.StartActivityRequest;
import sabbat.apigateway.location.controller.dto.StartActivityResponse;
import sabbat.apigateway.location.controller.dto.UpdateRequest;
import sabbat.location.core.application.IActivityApplicationService;

/**
 * Created by bruenni on 03.07.16.
 */
@Controller
// @ConfigurationProperties(prefix="application.mapmytracks.")
public class MapMyTracksApiController {

    private static Logger logger = LogManager.getLogger(MapMyTracksApiController.class);
    private IActivityApplicationService activityService;

    public MapMyTracksApiController(IActivityApplicationService activityService) {
        this.activityService = activityService;
    }

    @Value("${application.mapmytracks.text}")
    public String text;

    @RequestMapping(
            path = "/",
            method = RequestMethod.POST,
            produces={"application/xml"})
    public String postActivity(@RequestParam(value = "request") String requestType) throws Exception {
        logger.debug(StringFormatter.format("startActivity [{0}]", requestType));

        return "<message>" +
        "<type>activity_started</type>" +
        "<activity_id>9346</activity_id>" +
        "</message>";
    }

    //@RequestMapping("/")
    public MapMyTracksResponse update(UpdateRequest request) throws Exception {
        throw new Exception("Some exception");
    }
}
