package com.epam.polygor.webstore.action;


import com.epam.polygor.webstore.model.*;
import com.epam.polygor.webstore.service.PurchaseService;
import com.epam.polygor.webstore.servlet.Scope;
import com.epam.polygor.webstore.servlet.WebContext;

import java.sql.Timestamp;
import java.util.*;

public class ConfirmOrderAction implements Action {
    private ActionResult cartPage = new ActionResult("cart", true);

    @Override
    public ActionResult execute(WebContext webContext) {
        ResourceBundle messagesBundle = webContext.getMessagesBundle();
        Cart cart = (Cart) webContext.getSession().getAttribute("cart");
        User user = (User) webContext.getSession().getAttribute("user");
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        List<Product> products = cart.getProducts();
        List<Purchase> purchases = new ArrayList<>();
        for (Product product : products) {
            purchases.add(new Purchase(product, product.getPrice(), currentDate, user));
        }
        PurchaseService purchaseService = new PurchaseService();
        for (Purchase purchase : purchases) {
            purchaseService.addPurchase(purchase);
        }
        cart.removeAllProducts();
        webContext.setAttribute("successMessage", messagesBundle.getString("cart.message.success"), Scope.FLASH);
        return cartPage;
    }
}