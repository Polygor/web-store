package com.epam.polygor.webstore.action;

import com.epam.polygor.webstore.model.Order;
import com.epam.polygor.webstore.model.User;
import com.epam.polygor.webstore.service.PurchaseService;
import com.epam.polygor.webstore.servlet.Scope;
import com.epam.polygor.webstore.servlet.WebContext;

import java.util.List;

public class ShowUserPageAction implements Action {
    private ActionResult userPage = new ActionResult("user");

    @Override
    public ActionResult execute(WebContext webContext) {
        PurchaseService purchaseService = new PurchaseService();
        User user = (User) webContext.getAttribute("user", Scope.SESSION);
        List<Order> userOrderList = purchaseService.getUserOrderList(user.getId());
        webContext.setAttribute("purchaseList", userOrderList, Scope.REQUEST);
        return userPage;
    }
}
