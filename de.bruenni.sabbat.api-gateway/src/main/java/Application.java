import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import sabbat.apigateway.location.controller.MapMyTracksApiController;

/**
 * Created by bruenni on 03.07.16.
 */

//@SpringBootApplication
@Configuration
@ImportResource("classpath:spring/spring-all.xml")
public class Application {

    static final Logger logger = LogManager.getLogger(Application.class.getName());

    public static void main(String[] args) {
        logger.info("Starting api-gateway...");
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);

        MapMyTracksApiController controller = (MapMyTracksApiController) applicationContext.getBean("MapMyTracksApiController");
        logger.debug("controler.text=" + controller.text);
    }
}
