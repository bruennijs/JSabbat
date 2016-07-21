package sabbat.apigateway.location.config;

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
@Configuration
@Component
//@Configuration
public class MyDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Value("{application.mapmytracks.url")
    public String BaseUrl;

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{BaseUrl};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[] { new ServletContextRequestLoggingFilter() };
    }
}


