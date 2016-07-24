package org.springframework.web.filter;

import org.slf4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by bruenni on 24.07.16.
 */
public class ResponseLoggingFilter extends OncePerRequestFilter {

    Logger logger = org.slf4j.LoggerFactory.getLogger("location.traffic");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try
        {
        }
        finally
        {
            filterChain.doFilter(request, response);
        }
    }
}
