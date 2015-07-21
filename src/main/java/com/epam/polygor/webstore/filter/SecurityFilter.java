package com.epam.polygor.webstore.filter;

import com.epam.polygor.webstore.model.User;
import com.epam.polygor.webstore.servlet.Scope;
import com.epam.polygor.webstore.servlet.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SecurityFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(SecurityFilter.class);
    public static final String ADMIN_PAGE_PREFIX = "/admin";
    public static final String USER_PAGE_PREFIX = "/user";
    public static final String LOGIN_PAGE_NAME = "/login";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.debug("SECURITY FILTER");
        WebContext webContext = new WebContext(servletRequest, servletResponse);
        String path = webContext.getPathInfo();
        User user = (User) webContext.getAttribute("user", Scope.SESSION);
        log.debug("Path: " + path);
        log.debug("User: " + user);

        //if path is not secret and available for all go further
        if (!path.startsWith(ADMIN_PAGE_PREFIX) && !path.startsWith(USER_PAGE_PREFIX)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (user != null) {
            log.debug("User role: " + user.getRole());
            String roleName = user.getRole().name();
            if (!path.startsWith(ADMIN_PAGE_PREFIX) || roleName.equalsIgnoreCase(String.valueOf(User.Role.ADMINISTRATOR))) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        } else if (path.startsWith(USER_PAGE_PREFIX)) {
            webContext.sendRedirect(LOGIN_PAGE_NAME);
            return;
        }
        webContext.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
