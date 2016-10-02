package sabbat.apigateway.location.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.filter.Slf4jRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by bruenni on 17.07.16.
 * ONLY loaded on application startup , not is test
 */
@Configuration
//@Profile({"dev", "prod"})
@ImportResource(locations =
        {
                "classpath:spring/spring-api-gateway.xml"
        })
public class AppConfig {

    @Value(value = "${application.location.logpayload}")
    public Boolean logpayload;

/*    @Bean
    public WebApplicationInitializer dispatcherServletInitializer()
    {
        return new LocationDispatcherServletInitializer();
    }*/

    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        Slf4jRequestLoggingFilter filter = new Slf4jRequestLoggingFilter();

        filter.setIncludePayload(logpayload);

        filterRegistrationBean.setFilter(filter);
        //filterRegistrationBean.setUrlPatterns("/");
        return filterRegistrationBean;
    }
}
