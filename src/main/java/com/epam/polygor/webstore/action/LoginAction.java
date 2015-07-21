package com.epam.polygor.webstore.action;

import com.epam.polygor.webstore.model.User;
import com.epam.polygor.webstore.service.UserService;
import com.epam.polygor.webstore.servlet.Scope;
import com.epam.polygor.webstore.servlet.WebContext;

import java.util.ResourceBundle;

public class LoginAction implements Action {
    public static final String USER = "user";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private ActionResult loginActionSuccess = new ActionResult("catalog", true);
    private ActionResult loginActionError = new ActionResult("login", true);

    @Override
    public ActionResult execute(WebContext webContext) {
        UserService userService = new UserService();
        ResourceBundle messagesBundle = webContext.getMessagesBundle();
        String login = webContext.getParameter(LOGIN);
        String password = webContext.getParameter(PASSWORD);
        User authenticatedUser = userService.loginUser(login, password);
        if (login == null || password == null) {
            webContext.setAttribute(LOGIN, login, Scope.FLASH);
            webContext.setAttribute("errorMessage", messagesBundle.getString("login.error.emptyFields"), Scope.FLASH);
            return loginActionError;
        }
        if (authenticatedUser == null) {
            webContext.setAttribute(LOGIN, login, Scope.FLASH);
            webContext.setAttribute("errorMessage", messagesBundle.getString("login.error.notFound"), Scope.FLASH);
            return loginActionError;
        }
        if (authenticatedUser.isBanned()) {
            webContext.setAttribute("errorMessage", messagesBundle.getString("login.error.banned"), Scope.FLASH);
            return loginActionError;
        }
        webContext.setAttribute(USER, authenticatedUser, Scope.SESSION);
        return loginActionSuccess;
    }
}

