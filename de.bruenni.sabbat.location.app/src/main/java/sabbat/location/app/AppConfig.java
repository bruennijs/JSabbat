package sabbat.location.app;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by bruenni on 07.09.16.
 */
@Configuration
@PropertySource("classpath:application.properties")
@PropertySource("classpath:spring/sabbat-location-infrastructure.properties")
@ImportResource(locations =
        {
                "classpath:spring/spring-location-infrastructure.xml",
                //"classpath:spring/spring-location-amqp-client.xml",
                "classpath:spring/spring-location-amqp-service.xml"
        })
public class AppConfig {
}
