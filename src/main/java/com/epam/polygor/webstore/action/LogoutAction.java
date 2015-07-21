package com.epam.polygor.webstore.action;

import com.epam.polygor.webstore.servlet.Scope;
import com.epam.polygor.webstore.servlet.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutAction implements Action {
    private ActionResult backToStartPage = new ActionResult("catalog", true);

    @Override
    public ActionResult execute(WebContext webContext) {
        webContext.removeAttribute("user", Scope.SESSION);
        return backToStartPage;
    }
}


