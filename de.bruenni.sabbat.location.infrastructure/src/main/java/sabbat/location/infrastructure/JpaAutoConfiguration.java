package sabbat.location.infrastructure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;
import sabbat.location.core.persistence.activity.IActivityRepository;
import sabbat.location.infrastructure.persistence.activity.JpaActivityRepository;

/**
 * Created by bruenni on 24.09.16.
 */
@Configuration
public class JpaAutoConfiguration {

    @Bean(name = "jpaActivityRepository")
    public IActivityRepository jpaActivityRepository() {
        return new JpaActivityRepository();
    }
}
