package sabbat.apigateway.location.unittest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * Created by bruenni on 13.07.16.
 */
@Configuration
//@PropertySource("classpath:config/application.properties")
@ImportResource("classpath:test/spring-api-gateway-test.xml")
public class UnitTestConfig {
}
