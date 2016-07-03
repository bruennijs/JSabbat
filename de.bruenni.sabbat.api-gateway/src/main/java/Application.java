import org.apache.commons.logging.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.apache.logging.log4j.*;
import org.springframework.core.env.Environment;

/**
 * Created by bruenni on 03.07.16.
 */


public class Application {

    static final Logger logger = LogManager.getLogger(Application.class.getName());

    public static void main(String[] args) {
        logger.info("Starting Sabbat api-gateway");
        ConfigurableApplicationContext applicationContext = SpringApplication.run(null, args);

    }
}
