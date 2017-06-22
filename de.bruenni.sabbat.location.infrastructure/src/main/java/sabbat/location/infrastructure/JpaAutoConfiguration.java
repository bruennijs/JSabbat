package sabbat.location.infrastructure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;
import sabbat.location.core.persistence.activity.IActivityRepository;
import sabbat.location.infrastructure.persistence.activity.JpaActivityRepository;

/**
 * Created by bruenni on 24.09.16.
 */
@Configuration
//@ConditionalOnProperty(prefix = "location.infrastructure.jpa", name = "enabled", havingValue = "true", matchIfMissing = false)
@PropertySource("classpath:sabbat-location-infrastructure.properties")
public class JpaAutoConfiguration {

    @Bean(name = "jpaActivityRepository")
    public IActivityRepository jpaActivityRepository() {
        return new JpaActivityRepository();
    }
}
