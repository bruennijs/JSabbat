package sabbat.apigateway.location.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.filter.Slf4jRequestLoggingFilter;

/**
 * Created by bruenni on 17.07.16.
 * ONLY loaded on application startup , not is test
 */
@Configuration
@ImportResource(locations =
        {
                "classpath:spring/spring-api-gateway.xml"
        })
public class AppConfig {

    private Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Value(value = "${application.location.logpayload}")
    public Boolean logpayload;

/*    @Bean
    public WebApplicationInitializer dispatcherServletInitializer()
    {
        logger.debug("dispatcherServletInitializer");
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
