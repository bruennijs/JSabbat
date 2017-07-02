package configuration;

import com.stormpath.spring.security.provider.StormpathAuthenticationProvider;
import identity.IAuthenticationService;
import identity.implementation.JwtAuthenticationService;
import identity.implementation.StormpathFactory;
import infrastructure.identity.ITokenAuthentication;
import infrastructure.identity.implementation.JJwtTokenAuthenticationFactory;
import notification.EmailSender;
import notification.UserNotificationService;
import notification.implementation.EmailUserNotificationService;
import notification.implementation.JavaxMailEmailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import spring.security.SabbatJwtAuthenticationProvider;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by bruenni on 15.10.16.
 */
@Configuration
@PropertySource("classpath:sabbat-shared.properties")
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
                new User("bruenni", "bruenni", Arrays.asList(new SimpleGrantedAuthority("USERS"))),
                new User("user1@test.de", "Sabbat2017", Arrays.asList(new SimpleGrantedAuthority("USERS"))),
                new User("user2@test.de", "Sabbat2017", Arrays.asList(new SimpleGrantedAuthority("USERS"))));

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

    @Bean("sabbatSharedThreadPoolTaskExecutor")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Executor threadPoolTaskExecutor()
    {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(1);
        threadPoolTaskExecutor.setMaxPoolSize(2);
        threadPoolTaskExecutor.setMaxPoolSize(1000);
        return threadPoolTaskExecutor;
    }

    @Bean(name = "javaxEmailSender")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public EmailSender javaxEmailSender()
    {
        return new JavaxMailEmailSender();
    }

    @Bean(name = "emailUserNotificationService")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public UserNotificationService emailUserNotificationService(Executor sabbatSharedThreadPoolTaskExecutor, EmailSender emailSender)
    {
        return new EmailUserNotificationService(sabbatSharedThreadPoolTaskExecutor, emailSender);
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySource()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
