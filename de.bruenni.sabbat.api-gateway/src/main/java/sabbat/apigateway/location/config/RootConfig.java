package sabbat.apigateway.location.config;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

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
