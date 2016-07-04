package sabbat.apigateway.location.controller;


import com.sun.javafx.binding.StringFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import sabbat.apigateway.location.controller.dto.MapMyTracksResponse;
import sabbat.apigateway.location.controller.dto.StartActivityRequest;
import sabbat.apigateway.location.controller.dto.StartActivityResponse;
import sabbat.apigateway.location.controller.dto.UpdateRequest;
import sabbat.location.core.application.IActivityApplicationService;

/**
 * Created by bruenni on 03.07.16.
 */

// @Controller
// @ConfigurationProperties(prefix="application.mapmytracks.")
public class MapMyTracksApiController {

    private static Logger logger = LogManager.getLogger(MapMyTracksApiController.class);
    private IActivityApplicationService activityService;

    public MapMyTracksApiController(IActivityApplicationService activityService) {
        this.activityService = activityService;
    }

    @Value("${application.mapmytracks.period}")
    public String period;

    @Value("${application.mapmytracks.text}")
    public String text;

    //@RequestMapping("/")
    public StartActivityResponse startActivity(StartActivityRequest request) throws Exception {
        logger.debug(StringFormatter.format("startActivity [{0}]", request));
        throw new Exception("Some exception");
    }

    //@RequestMapping("/")
    public MapMyTracksResponse update(UpdateRequest request) throws Exception {
        throw new Exception("Some exception");
    }
}
