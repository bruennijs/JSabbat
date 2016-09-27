package sabbat.apigateway.location.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.filter.Slf4jRequestLoggingFilter;

/**
 * Created by bruenni on 17.07.16.
 * ONLY loaded on application startup , not is test
 */
@Configuration
//@Profile({"dev", "prod"})
@ImportResource(locations =
        {
                "classpath:spring/spring-api-gateway.xml",
                "classpath:spring/spring-location-infrastructure.xml",
                "classpath:spring/spring-location-amqp-client.xml"
        })
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value(value = "${application.location.logpayload}")
    public Boolean logpayload;

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
