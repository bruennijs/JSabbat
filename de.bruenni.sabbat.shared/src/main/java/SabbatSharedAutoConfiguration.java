package sabbat.shared;

import identity.IAuthenticationService;
import identity.implementation.JwtAuthenticationService;
import identity.implementation.StormpathAuthenticationService;
import infrastructure.identity.ITokenAuthentication;
import infrastructure.identity.implementation.JJwtTokenAuthentication;
import infrastructure.identity.implementation.JJwtTokenAuthenticationFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.Order;

/**
 * Created by bruenni on 15.10.16.
 */
@Configuration
public class SabbatSharedAutoConfiguration {
    @Bean(name = "verifyingTokenAuthentication")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ITokenAuthentication tokenAuthentication()
    {
        return JJwtTokenAuthenticationFactory.createWithoutSign();
    }

    @Bean(name = "issueingAuthenticationService")
    @Lazy
    //@ConditionalOnProperty(prefix = "sabbat.shared.isAuthenticationAuthority", value = "enabled", havingValue = "true", matchIfMissing = false)
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public IAuthenticationService issueingAuthenticationService(ITokenAuthentication tokenAuthentication)
    {
        return new StormpathAuthenticationService(tokenAuthentication);
    }

    @Bean(name = "verifyingAuthenticationService")
    @Lazy
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public IAuthenticationService verifyingAuthenticationService(ITokenAuthentication tokenAuthentication)
    {
        return new JwtAuthenticationService(tokenAuthentication);
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySource()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
