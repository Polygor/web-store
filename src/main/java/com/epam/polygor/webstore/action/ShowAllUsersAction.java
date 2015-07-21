package com.epam.polygor.webstore.action;

import com.epam.polygor.webstore.model.User;
import com.epam.polygor.webstore.service.UserService;
import com.epam.polygor.webstore.servlet.Scope;
import com.epam.polygor.webstore.servlet.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

public class ShowAllUsersAction implements Action {
    private ActionResult adminPage = new ActionResult("admin");

    @Override
    public ActionResult execute(WebContext webContext) {
        UserService userService = new UserService();
        List<User> allUsers = userService.getAllUsers();
        List<User> notBannedUsers = allUsers.stream().filter(user -> !user.isBanned()).collect(Collectors.toList());
        webContext.setAttribute("users", notBannedUsers, Scope.REQUEST);
        return adminPage;
    }
}

