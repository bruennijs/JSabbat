package sabbat.apigateway;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.context.ApplicationContext;
import sabbat.apigateway.location.config.AppConfig;
import sabbat.apigateway.location.config.RootConfig;
import sabbat.apigateway.location.config.WebConfig;
import sabbat.location.infrastructure.AmqpClientAutoConfiguration;
import sabbat.location.infrastructure.client.configuration.ActivityMiddlewareConfig;

import java.util.Arrays;

/**
 * Created by bruenni on 03.07.16.
 */

@SpringBootApplication(exclude =
        {
                CassandraAutoConfiguration.class,
                CassandraDataAutoConfiguration.class
        })
// contains componentscan to find @Configuratuion annotated class -> instead give spring boot these classes by calling SpringApplication.run(...) with these classes
//@EnableAutoConfiguration
//@ComponentScan(basePackages = "sabbat.apigateway.location.config")
public class Application {

    static final Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {

        Arrays.stream(args).forEach(arg -> logger.info("arg=" + arg));

        logger.info("Starting api-gateway...");
        ApplicationContext applicationContext = SpringApplication.run(new Object[] {
                RootConfig.class,
                WebConfig.class,
                AppConfig.class}, args);

        //Environment environment = Environment;

/*        MapMyTracksApiController controller = (MapMyTracksApiController) applicationContext.getBean("MapMyTracksApiController");
        logger.debug("controler.text=" + controller.text + ",perido=" + controller.period);*/
    }
}
