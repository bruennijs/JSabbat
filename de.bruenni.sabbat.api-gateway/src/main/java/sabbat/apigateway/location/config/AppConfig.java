package sabbat.apigateway.location.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * Created by bruenni on 17.07.16.
 * ONLY loaded on application startup , not is test
 */
@Configuration
@ImportResource("classpath:spring/spring-api-gateway.xml")
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value(value = "${application.location.logpayload}")
    public Boolean logpayload;

    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();

        filter.setIncludePayload(logpayload);

        filterRegistrationBean.setFilter(filter);
        //filterRegistrationBean.setUrlPatterns("/");
        return filterRegistrationBean;
    }
}
