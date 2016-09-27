package sabbat.apigateway.location.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * Created by bruenni on 17.07.16.
 */
@Configuration
public class RootConfig {

    @Value(value = "${application.location.logpayload}")
    public Boolean logpayload;

/*    @Bean
    public ServletRegistrationBean dispatcherServlet() {
        ServletRegistrationBean registration = new ServletRegistrationBean(
                new DispatcherServlet(), "/");
        registration.setAsyncSupported(false);
        return registration;
    }*/

/*    @Bean
    public org.springframework.boot.context.embedded.ServletContextInitializer servletContextInitializer()
    {
        return new MyEmbeddedContainerServletContextInitializer();
    }*/

/*    @Bean
    public TomcatEmbeddedServletContainerFactory embeddedServletContainer()
    {
        return new TomcatEmbeddedServletContainerFactory();
    }*/
}
