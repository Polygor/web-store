package com.epam.polygor.webstore.filter;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilter implements Filter {
    private static final String DEFAULT_ENCODING = "utf-8";
    private String currentEncoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        currentEncoding = filterConfig.getInitParameter("encoding");
        if (currentEncoding == null) {
            currentEncoding = DEFAULT_ENCODING;
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(currentEncoding);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}