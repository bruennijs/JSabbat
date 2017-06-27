package sabbat.location.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by bruenni on 07.09.16.
 */
@Configuration
//@PropertySource("classpath:application.properties")
@ImportResource(locations =
        {
                "classpath:spring/spring-location-app.xml"
        })
public class AppConfig {
}
