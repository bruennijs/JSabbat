import com.stormpath.spring.security.provider.StormpathAuthenticationProvider;
import identity.IAuthenticationService;
import identity.implementation.JwtAuthenticationService;
import identity.implementation.StormpathAuthenticationService;
import identity.implementation.StormpathFactory;
import infrastructure.identity.AuthenticationFailedException;
import infrastructure.identity.ITokenAuthentication;
import infrastructure.identity.implementation.JJwtTokenAuthenticationFactory;
import notification.NotificationService;
import notification.implementation.EmailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import spring.security.SabbatJwtAuthenticationProvider;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bruenni on 15.10.16.
 */
@Configuration
@PropertySource("classpath:/sabbat/shared/default.properties")
public class SabbatSharedAutoConfiguration {

    @Value("${stormpath.application.name}")
    public String StormpathApplicationName;

    @Bean(name = "verifyingTokenAuthentication")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ITokenAuthentication tokenAuthentication()
    {
        return JJwtTokenAuthenticationFactory.createWithoutSign();
    }

    @Bean(name = "verifyingAuthenticationService")
    @Lazy
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public IAuthenticationService verifyingAuthenticationService(ITokenAuthentication tokenAuthentication)
    {
        return new JwtAuthenticationService(tokenAuthentication);
    }

    @Bean(name = "stormpathFactory")
    @ConditionalOnProperty(prefix = "sabbat.shared", value = "authenticationprovider", havingValue = "stormpath", matchIfMissing = false)
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public StormpathFactory stormpathFactory()
    {
        StormpathFactory stormpathFactory = new StormpathFactory();
        stormpathFactory.connect();
        return stormpathFactory;
    }

    @Bean(name = "verifyingAuthenticationProvider")
    @ConditionalOnProperty(prefix = "sabbat.shared", value = "authenticationprovider", havingValue = "inmemory", matchIfMissing = false)
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AuthenticationProvider inMemoryTestAthenticationProvider() throws Exception {

        List<UserDetails> userList = Arrays.asList(new User("test", "password", Arrays.asList(new SimpleGrantedAuthority("USERS"))),
                new User("anmema", "anmema", Arrays.asList(new SimpleGrantedAuthority("USERS"))),
                new User("bruenni", "bruenni", Arrays.asList(new SimpleGrantedAuthority("USERS"))));

        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager(userList);
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
        //throw new Exception("sabbat.shared.authenticationprovider == inmemory must be implemented first in SabbatSharedAutoConfiguration");
    }

    @Bean(name = "verifyingAuthenticationProvider")
    @ConditionalOnProperty(prefix = "sabbat.shared", value = "authenticationprovider", havingValue = "stormpath", matchIfMissing = false)
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AuthenticationProvider stormpathAuthenticationProvider()
    {
        return new StormpathAuthenticationProvider(stormpathFactory().getApplication(this.StormpathApplicationName));
    }

    @Bean(name = "authenticationProvider")
    @ConditionalOnBean(name = "verifyingAuthenticationProvider")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public SabbatJwtAuthenticationProvider sabbatJwtIssueingAuthenticationProvider(ITokenAuthentication verifyingTokenAuthentication, AuthenticationProvider verifyingAuthenticationProvider)
    {
        return new SabbatJwtAuthenticationProvider(verifyingTokenAuthentication, verifyingAuthenticationProvider);
    }

    @Bean(name = "emailNotificationService")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public NotificationService emailNotificationService()
    {
        return new EmailNotificationService();
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySource()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
