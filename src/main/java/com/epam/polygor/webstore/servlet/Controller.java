package com.epam.polygor.webstore.servlet;

import com.epam.polygor.webstore.action.Action;
import com.epam.polygor.webstore.action.ActionFactory;
import com.epam.polygor.webstore.action.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(Controller.class);
    private ActionFactory actionFactory;
    private WebContext webContext;
    @Override
    public void init() throws ServletException {
        actionFactory = ActionFactory.getInstance();
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        webContext = new WebContext(req, resp);
        log.info("Requested action: " + webContext.getRequestedAction());
        log.info("Context path: " + req.getContextPath());
        log.info("current URI: " + webContext.getRequestURI());
        log.info("Referrer: " + req.getHeader("Referrer"));
        Action action = actionFactory.getAction(webContext);
        if (action == null) {
            log.debug("Action not found");
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        log.info("Found action: " + action.getClass().getSimpleName());
        ActionResult result = action.execute(webContext);
        doForwardOrRedirect(result, req, resp);
    }

    private void doForwardOrRedirect(ActionResult result, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (result.isRedirect()) {
            String location = webContext.getContextPath() + "/" + result.getPageName();
            log.info("Redirect requested location: " + location);
            webContext.sendRedirect(location);
        } else {
            String path = "/WEB-INF/jsp/" + result.getPageName() + ".jsp";
            log.info("Forward requested path: " + path);
            webContext.forward(path);
        }
    }
}