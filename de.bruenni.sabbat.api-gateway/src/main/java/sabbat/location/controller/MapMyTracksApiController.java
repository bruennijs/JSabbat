package sabbat.location.controller;


import com.sun.javafx.binding.StringFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import sabbat.location.controller.dto.MapMyTracksResponse;
import sabbat.location.controller.dto.StartActivityRequest;
import sabbat.location.controller.dto.StartActivityResponse;
import sabbat.location.controller.dto.UpdateRequest;

/**
 * Created by bruenni on 03.07.16.
 */
@Controller
// @ConfigurationProperties(prefix="application.mapmytracks.")
public class MapMyTracksApiController {

    private static Logger logger = LogManager.getLogger(MapMyTracksApiController.class);

    @Value("${application.mapmytracks.period}")
    public int period;

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
