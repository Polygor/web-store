package com.epam.polygor.webstore.servlet;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.*;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Facade, works with {@link HttpServletRequest} and
 * {@link HttpServletResponse} classes.
 *
 * @see javax.servlet.http.HttpServletRequest
 * @see javax.servlet.http.HttpServletResponse
 */

public class WebContext {
    private static final Logger log = LoggerFactory.getLogger(WebContext.class);
    private static final String FLASH_ATTRIBUTE_PREFIX = "flash.";
    private static final String MESSAGES_BUNDLE_BASENAME = "i18n.MessagesBundle";
    private static final String LANGUAGE_COOKIE_ATTRIBUTE = "lang";
    private static final String LOCALE_ATTRIBUTE_NAME = "locale";
    private static final String SPLIT_URL_PATH_REGEX = "[^/]+";
    private static final List<String> pagesWithURIParameters;

    private HttpServletRequest req;
    private HttpServletResponse resp;

        static {
            pagesWithURIParameters = new ArrayList<>();
            pagesWithURIParameters.add("catalog");
            pagesWithURIParameters.add("image");
            pagesWithURIParameters.add("purchase-list");
            pagesWithURIParameters.add("products");
            pagesWithURIParameters.add("details");
        }
    public WebContext(HttpServletRequest req, HttpServletResponse resp) {
        this.req = req;
        this.resp = resp;
    }

    public WebContext(ServletRequest servletRequest, ServletResponse servletResponse) {
        this((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse);
    }

    public String getParameter(String parameterName) {
        return req.getParameter(parameterName);
    }

    public String[] getParameterValues(String name) {
        return req.getParameterValues(name);
    }

    public void setAttribute(String name, Object value, Scope scope) {
        switch (scope) {
            case REQUEST:
                req.setAttribute(name, value);
                break;
            case SESSION:
                req.getSession().setAttribute(name, value);
                break;
            case APPLICATION:
                req.getServletContext().setAttribute(name, value);
                break;
            case FLASH:
                req.getSession().setAttribute(addFlashPrefixToName(name), value);
                break;
        }
    }

    public Object getAttribute(String name, Scope scope) {
        Object attributeObject = null;
        switch (scope) {
            case REQUEST:
                attributeObject = req.getAttribute(name);
                break;
            case SESSION:
                if (isSessionExist()) {
                    attributeObject = req.getSession().getAttribute(name);
                }
                break;
            case APPLICATION:
                attributeObject = req.getServletContext().getAttribute(name);
                break;
            case FLASH:
                if (isSessionExist()) {
                    attributeObject = req.getSession().getAttribute(addFlashPrefixToName(name));
                    if (attributeObject == null) attributeObject = req.getAttribute(name);
                }
                break;
        }
        return attributeObject;
    }

    public void removeAttribute(String name, Scope scope) {
        switch (scope) {
            case REQUEST:
                req.removeAttribute(name);
                break;
            case SESSION:
                if (isSessionExist()) {
                    req.getSession().removeAttribute(name);
                }
                break;
            case APPLICATION:
                req.getServletContext().removeAttribute(name);
                break;
            case FLASH:
                if (isSessionExist()) {
                    req.getSession().removeAttribute(addFlashPrefixToName(name));
                }
                break;
        }
    }

    public List<String> getAttributeNames(Scope scope) {
        List<String> attributeNames = new ArrayList<>();
        switch (scope) {
            case REQUEST:
                attributeNames = getListFromEnumeration(req.getAttributeNames());
                break;
            case SESSION:
                if (isSessionExist()) {
                    attributeNames = getListFromEnumeration(req.getSession().getAttributeNames());
                }
                break;
            case APPLICATION:
                attributeNames = getListFromEnumeration(req.getServletContext().getAttributeNames());
                break;
            case FLASH:
                if (isSessionExist()) {
                    Enumeration<String> attributeEnumeration = req.getSession().getAttributeNames();
                    attributeNames = getAttributeNamesWithFlashPrefix(attributeEnumeration);
                }
                break;
        }
        return attributeNames;
    }

    public String getRequestedAction() {
        return req.getMethod() + getPagePathFromURN();
    }


    private <T> List<T> getListFromEnumeration(Enumeration<T> e) {
        List<T> list = new ArrayList<>();
        while (e.hasMoreElements()) {
            T element = e.nextElement();
            list.add(element);
        }
        return list;
    }
    /**
     * Getting attribute names with flash-prefix from the enumeration
     * then vanishing prefix from them and collecting to the list
     *
     * @param enumeration the enumeration of the attribute names
     * @return List of flash attribute names without flash prefix
     */
    private List<String> getAttributeNamesWithFlashPrefix(Enumeration<String> enumeration) {
        List<String> attributeNames = new ArrayList<>(getListFromEnumeration(enumeration));
        //get names with flash-prefix
        Iterator<String> iterator = attributeNames.iterator();
        while (iterator.hasNext()) {
            String attributeName = iterator.next();
            if (!attributeName.startsWith(FLASH_ATTRIBUTE_PREFIX)) {
                iterator.remove();
            }
        }
        //remove flash-prefix from all names
        attributeNames.replaceAll(s -> s.substring(FLASH_ATTRIBUTE_PREFIX.length()));
        return attributeNames;
    }

    public void forward(String path) throws ServletException, IOException {
        req.getRequestDispatcher(path).forward(req, resp);
    }
    public String getFirstParameterFromPath() {
        List<String> parametersFromURI = getParametersFromPath();
        if (parametersFromURI.size() == 0) return null;
        return parametersFromURI.iterator().next();
    }

    public List<String> getParametersFromPath() {
        String pathInfo = req.getPathInfo();
        String parameterString = pathInfo.substring(getPagePathFromURN().length());
        return splitIntoSegments(parameterString);
    }

    public String getPagePathFromURN() {
        List<String> pathSegments = splitIntoSegments(req.getPathInfo());
        StringBuilder sb = new StringBuilder();
        for (String segment : pathSegments) {
            sb.append("/");
            sb.append(segment);
            if (pagesWithURIParameters.contains(segment)) {
                return sb.toString();
            }
        }
        if (sb.length() == 0) sb.append("/");
        return sb.toString();
    }

    public void sendRedirect(String location) throws IOException {
        resp.sendRedirect(location);
    }

    public void sendError(int error) throws IOException {
        resp.sendError(error);
    }
    public String getContextPath() {
        return req.getContextPath();
    }

    public String getPreviousURI() {
        return req.getHeader("Referer").substring(getURL().length());
    }

    public String getURL() {
        StringBuilder sb = new StringBuilder();
        sb.append(req.getScheme());
        sb.append("://");
        sb.append(req.getServerName());
        sb.append(":");
        sb.append(req.getServerPort());
        sb.append("/");
        return sb.toString();
    }

    public String getRequestURI() {
        return req.getRequestURI();
    }


    public String getPathInfo() {
        return req.getPathInfo();
    }

    public Part getPart(String fileName) throws IOException, ServletException {
          return req.getPart(fileName);
    }


    public String getContentType() {
        return req.getContentType();
    }

    public void setContentType(String content) {
        resp.setContentType(content);
    }

    public String getMethod() {
        return req.getMethod();
    }

    public ResourceBundle getMessagesBundle() {
        return ResourceBundle.getBundle(MESSAGES_BUNDLE_BASENAME, getCurrentLocale());
    }

    public Locale getCurrentLocale() {
        return (Locale) req.getSession().getAttribute("locale");
    }

    public void setJstlFormatterLocale(Locale locale) {
        Config.set(req.getSession(), Config.FMT_LOCALE, locale);
    }

    public void addCookie(Cookie cookie) {
        resp.addCookie(cookie);
    }


    public void addLangCookie(Locale locale) {
        Cookie cookie = new Cookie(LANGUAGE_COOKIE_ATTRIBUTE, locale.getLanguage());
        cookie.setMaxAge(Integer.MAX_VALUE);
        addCookie(cookie);
    }

    public Cookie findCookie(String name) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : req.getCookies()) {
                if (name.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public void setLocale(Locale locale) {
        setAttribute(LOCALE_ATTRIBUTE_NAME, locale, Scope.SESSION);
        setJstlFormatterLocale(locale);
    }

    public HttpSession getSession() {
        return req.getSession();
    }

    private List<String> splitIntoSegments(String uri) {
        List<String> names = new ArrayList<>();
        String regex = SPLIT_URL_PATH_REGEX;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(uri);
        while (matcher.find()) {
            String parameter = matcher.group();
            if (parameter.length() > 0) names.add(matcher.group());
        }
        return names;
    }

    private String addFlashPrefixToName(String attributeName) {
        return FLASH_ATTRIBUTE_PREFIX + attributeName;
    }

    private boolean isSessionExist() {
        return req.getSession(false) != null;
    }

}
