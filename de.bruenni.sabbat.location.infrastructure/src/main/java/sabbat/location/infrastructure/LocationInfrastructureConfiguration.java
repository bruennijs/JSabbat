package sabbat.location.infrastructure;

import infrastructure.parser.JsonDtoParser;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

/**
 * Created by bruenni on 05.08.16.
 */
@Configuration
@PropertySource("classpath:sabbat-location-infrastructure.properties")
@ImportResource(locations = {"classpath:spring/spring-location-infrastructure.xml"})
public class LocationInfrastructureConfiguration {

    @Bean(name = "locationJsonDtoParser")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public JsonDtoParser locationJsonDtoParser()
    {
        return new JsonDtoParser();
    }
}
