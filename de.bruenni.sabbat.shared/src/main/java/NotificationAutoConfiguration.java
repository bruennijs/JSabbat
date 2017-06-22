import com.stormpath.spring.security.provider.StormpathAuthenticationProvider;
import identity.IAuthenticationService;
import identity.implementation.JwtAuthenticationService;
import identity.implementation.StormpathFactory;
import infrastructure.identity.ITokenAuthentication;
import infrastructure.identity.implementation.JJwtTokenAuthenticationFactory;
import notification.NotificationService;
import notification.implementation.EmailNotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import spring.security.SabbatJwtAuthenticationProvider;

import java.util.Arrays;
import java.util.List;

/**
 * Created by bruenni on 15.10.16.
 */
@Configuration
@PropertySource("classpath:/sabbat/shared/default.properties")
public class NotificationAutoConfiguration {

    @Bean(name = "emailNotificationService")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public NotificationService emailNotificationService()
    {
        return new EmailNotificationService();
    }
}
