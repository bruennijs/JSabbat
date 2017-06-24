package sabbat.location.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import sabbat.location.core.persistence.activity.IActivityRepository;
import sabbat.location.infrastructure.persistence.activity.JpaActivityRepository;

/**
 * Created by bruenni on 24.09.16.
 */
@Configuration
@Profile(value = {"dev"})
@PropertySource("classpath:application-dev.properties")
public class JpaDevAutoConfiguration {
}
