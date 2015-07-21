package com.epam.polygor.webstore.action;

import com.epam.polygor.webstore.service.UserService;
import com.epam.polygor.webstore.servlet.WebContext;

public class SetUserBanAction implements Action {
    @Override
    public ActionResult execute(WebContext webContext) {
        UserService userService = new UserService();
        String userIdParameter = webContext.getParameter("id");
        String banStringValue = webContext.getParameter("banValue");
        boolean banValue = Boolean.valueOf(banStringValue);
        if (userIdParameter != null && !userIdParameter.isEmpty()) {
            long userID = Long.valueOf(userIdParameter);
            userService.setUserBan(userID, banValue);
        }
        return new ActionResult(webContext.getPreviousURI(), true);
    }
}
