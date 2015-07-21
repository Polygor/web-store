package com.epam.polygor.webstore.filter;

import com.epam.polygor.webstore.servlet.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class ResourceFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(ResourceFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        WebContext webContext = new WebContext(servletRequest, servletResponse);
        String path = webContext.getRequestURI();
        if (path.startsWith("/static/")) {
            log.debug("Filtered static resource: " + path);
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (path.startsWith("/image/")) {
            log.debug("Forward to image servlet: " + path);
            webContext.forward("/image" + path);
        } else {
            log.debug("Forward to controller: " + path);
            webContext.forward("/controller" + path);
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void destroy() {

    }
}
