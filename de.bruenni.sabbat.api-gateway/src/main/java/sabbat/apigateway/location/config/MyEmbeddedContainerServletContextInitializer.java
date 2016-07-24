package sabbat.apigateway.location.config;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * Created by bruenni on 07.07.16.
 */
public class MyEmbeddedContainerServletContextInitializer implements ServletContextInitializer {

    static final Logger logger = LogManager.getLogger(MyEmbeddedContainerServletContextInitializer.class.getName());

    @Override
    public void onStartup(ServletContext container) {
        logger.info("constructor: stub implementation");
    }

}
