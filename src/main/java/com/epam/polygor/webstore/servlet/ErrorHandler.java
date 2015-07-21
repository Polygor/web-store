package com.epam.polygor.webstore.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorHandler extends HttpServlet {
    public static final Logger log = LoggerFactory.getLogger(ErrorHandler.class);
    private static final String ERROR_PAGE = "WEB-INF/jsp/error.jsp";
    private static final String NO_ACCESS_PAGE = "WEB-INF/jsp/restricted.jsp";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext webContext = new WebContext(req, resp);
        Throwable throwable = (Throwable) webContext.getAttribute("javax.servlet.error.exception", Scope.REQUEST);
        Integer statusCode = (Integer) webContext.getAttribute("javax.servlet.error.status_code", Scope.REQUEST);
        String requestUri = (String) webContext.getAttribute("javax.servlet.error.request_uri", Scope.REQUEST);
        if (requestUri != null) {
            log.warn("Error when accessing: " + requestUri);
        }
        if (throwable != null) {
            log.warn("Handled exception:", throwable);
        }

        if (statusCode != null) {
            log.warn("Error status code:" + statusCode);
            webContext.setAttribute("statusCode", statusCode, Scope.REQUEST);
            if (statusCode == 403) {
                webContext.forward(NO_ACCESS_PAGE);
                return;
            }
        }

        webContext.forward(ERROR_PAGE);
    }
}
