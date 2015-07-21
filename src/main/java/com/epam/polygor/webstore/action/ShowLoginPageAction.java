package com.epam.polygor.webstore.action;


import com.epam.polygor.webstore.model.User;
import com.epam.polygor.webstore.servlet.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShowLoginPageAction implements Action {
    public static final String USER = "user";
    private ActionResult login = new ActionResult("login");
    private ActionResult catalog = new ActionResult("catalog", true);

    @Override
    public ActionResult execute(WebContext webContext) {
        HttpSession session = webContext.getSession();
        User user = (User) session.getAttribute(USER);
        if (user != null) return catalog;

        return login;
    }
}