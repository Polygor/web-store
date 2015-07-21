package com.epam.polygor.webstore.action;

import com.epam.polygor.webstore.model.Order;
import com.epam.polygor.webstore.model.Purchase;
import com.epam.polygor.webstore.model.User;
import com.epam.polygor.webstore.service.PurchaseService;
import com.epam.polygor.webstore.service.UserService;
import com.epam.polygor.webstore.servlet.Scope;
import com.epam.polygor.webstore.servlet.WebContext;

import java.util.List;

public class ShowUserOrderList implements Action {
    private ActionResult actionResult = new ActionResult("user-orders");

    @Override
    public ActionResult execute(WebContext webContext) {
        String userStringID = webContext.getFirstParameterFromPath();
        UserService userService = new UserService();
        PurchaseService purchaseService = new PurchaseService();
        if (userStringID != null && Validator.isIntegerNumber(userStringID)) {
            Long userID = Long.valueOf(userStringID);
            User user = userService.findUser(userID);
            List<Order> userOrderList = purchaseService.getUserOrderList(userID);
            webContext.setAttribute("user", user, Scope.REQUEST);
            webContext.setAttribute("orderList", userOrderList, Scope.REQUEST);
        }
        return actionResult;
    }
}