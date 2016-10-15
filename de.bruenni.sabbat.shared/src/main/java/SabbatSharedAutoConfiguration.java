import infrastructure.identity.ITokenAuthentication;
import infrastructure.identity.implementation.JJwtTokenAuthentication;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Created by bruenni on 15.10.16.
 */
@Configuration
public class SabbatSharedAutoConfiguration {
    @Bean(name = "verifyingTokenAuthentication")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ITokenAuthentication tokenAuthentication()
    {
        return new JJwtTokenAuthentication();
    }
}
