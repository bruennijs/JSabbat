package org.springframework.web.filter;

import org.slf4j.Logger;
import sabbat.apigateway.location.controller.MapMyTracksApiController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by bruenni on 21.07.16.
 */
public class Slf4jRequestLoggingFilter extends AbstractRequestLoggingFilter {

    final Logger logger = org.slf4j.LoggerFactory.getLogger("location.traffic");

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return logger.isInfoEnabled();
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        logger.info(message);
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        logger.info(message);
    }
}
