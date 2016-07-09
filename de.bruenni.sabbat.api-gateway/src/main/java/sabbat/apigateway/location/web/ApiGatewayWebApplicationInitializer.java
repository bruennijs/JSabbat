package sabbat.apigateway.location.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

/**
 * Created by bruenni on 05.07.16.
 */


public class ApiGatewayWebApplicationInitializer extends AbstractDispatcherServletInitializer {

    @Value("{application.mapmytracks.url")
    public String BaseUrl;

    public ApiGatewayWebApplicationInitializer() {

    }

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        return null;
    }

    @Override
    protected WebApplicationContext createServletApplicationContext() {
/*        XmlWebApplicationContext cxt = new XmlWebApplicationContext();
        cxt.setConfigLocation("/WEB-INF/spring/dispatcher-config.xml");*/
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{BaseUrl};
    }
}


