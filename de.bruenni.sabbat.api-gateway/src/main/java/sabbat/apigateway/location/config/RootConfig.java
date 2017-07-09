package sabbat.apigateway.location.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by bruenni on 17.07.16.
 */
@Configuration
public class RootConfig {

    Logger logger = org.slf4j.LoggerFactory.getLogger(RootConfig.class);

    @Value(value = "${application.location.logpayload}")
    public Boolean logpayload;

    @Value(value = "${server.port}")
    public int serverPort;


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
        return new TomcatEmbeddedServletContainerFactory(serverPort);
    }*/
}
