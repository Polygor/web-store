package com.epam.polygor.webstore.filter;

import com.epam.polygor.webstore.servlet.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

public class LanguageFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(LanguageFilter.class);
    private static final String RU_LANGUAGE = "ru";
    private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        WebContext webContext = new WebContext(servletRequest, servletResponse);
        HttpSession session = webContext.getSession();
        if (session.isNew()) {
            Cookie lang = webContext.findCookie("lang");
            if (lang != null) {
                String value = lang.getValue();
                Locale localeFromCookie;
                if (value.equalsIgnoreCase(RU_LANGUAGE)) {
                    localeFromCookie = new Locale("ru", "RU");
                } else {
                    localeFromCookie = new Locale(value);
                }
                log.debug("Setting locale: " + localeFromCookie);
                webContext.setLocale(localeFromCookie);
            }
        } else if (webContext.getCurrentLocale() == null) {
            log.debug("Setting default locale: " + DEFAULT_LOCALE);
            webContext.setLocale(DEFAULT_LOCALE);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
