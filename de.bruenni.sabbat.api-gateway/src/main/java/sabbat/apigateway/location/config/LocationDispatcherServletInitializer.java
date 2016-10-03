package sabbat.apigateway.location.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;
import org.springframework.web.filter.ServletContextRequestLoggingFilter;

import javax.servlet.Filter;
import java.util.Arrays;

/**
 * Created by bruenni on 05.07.16.
 */
public class LocationDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private Logger logger = LoggerFactory.getLogger(LocationDispatcherServletInitializer.class);

    @Value("{location.mapmytracksapi.baseurl")
    public String BaseUrl;

    public LocationDispatcherServletInitializer() {
        logger.debug("LocationDispatcherServletInitializer ctor");
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        logger.debug("getRootConfigClasses");
        return new Class<?>[] {RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses()
    {
        logger.debug("getServletConfigClasses");
        return new Class<?>[] {WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        logger.debug("getServletMappings");
        return new String[]{"/location/api/v1"};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]
                {
                        new ServletContextRequestLoggingFilter()
                };
    }

}


