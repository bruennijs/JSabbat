package sabbat.location.infrastructure;

import infrastructure.parser.JsonDtoParser;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;

/**
 * Created by bruenni on 05.08.16.
 */
@Configuration
@ImportResource(locations = {"classpath:spring/spring-location-infrastructure.xml"})
public class LocationInfrastructureConfiguration {

    @Bean(name = "locationJsonDtoParser")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public JsonDtoParser locationJsonDtoParser()
    {
        return new JsonDtoParser();
    }
}
