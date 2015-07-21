package com.epam.polygor.webstore.action;

import com.epam.polygor.webstore.servlet.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private static ActionFactory instance = new ActionFactory();
    private Map<String, Action> actions;
    private static final Logger log = LoggerFactory.getLogger(ActionFactory.class);

    static public ActionFactory getInstance() {
        return instance;
    }

    private ActionFactory() {
        actions = new HashMap<>();
        actions.put("GET/", new ShowCatalogPageAction());
        actions.put("GET/catalog", new ShowCatalogPageAction());
        actions.put("GET/admin", new ShowPageAction("admin"));
        actions.put("GET/cart", new ShowPageAction("cart"));
        actions.put("GET/details", new ShowProductDetailsAction());
        actions.put("GET/user", new ShowUserPageAction());
        actions.put("GET/changeLang", new ChangeLanguageAction());
        actions.put("GET/registration", new ShowPageAction("registration"));
        actions.put("POST/registration", new RegistrationAction());
        actions.put("GET/registration-success", new ShowPageAction("registration-success"));
        actions.put("GET/error", new ShowPageAction("error"));
        actions.put("GET/accessDenied", new ShowPageAction("restricted"));
        actions.put("GET/login", new ShowLoginPageAction());
        actions.put("POST/login", new LoginAction());
        actions.put("GET/logout", new LogoutAction());
        actions.put("GET/user/change-password", new ShowPageAction("change-password"));
        actions.put("POST/user/changePassword", new ChangePasswordAction());
        actions.put("GET/addToCart", new ShowPageAction("addToCart"));
        actions.put("GET/productDetails", new ShowProductDetailsAction());
        actions.put("POST/addToCart", new AddToCartAction());
        actions.put("POST/deleteFromCart", new DeleteFromCartAction());
        actions.put("POST/deleteProducts", new DeleteProductsAction());
        actions.put("POST/user/confirmOrder", new ConfirmOrderAction());
        actions.put("GET/admin/categories", new ShowPageAction("category-management"));
        actions.put("GET/admin/product-creation", new ShowProductCreationPageAction());
        actions.put("POST/admin/product-creation/create", new CreateProductAction());
        actions.put("POST/admin/changePurchaseStatus", new ChangeOrderStatusAction());
        actions.put("POST/admin/categories/create", new CreateCategoryAction());
        actions.put("POST/admin/categories/delete", new DeleteCategoryAction());
        actions.put("GET/admin/products", new ShowProductsTableAction());
        actions.put("GET/admin/order-list", new ShowUserOrderList());
        actions.put("GET/admin/orders", new ShowUserOrderList());
        actions.put("GET/admin/users", new ShowAllUsersAction());
        actions.put("GET/admin/products", new ShowProductsTableAction());
        actions.put("GET/admin/black-list", new ShowBannedUsers());
        actions.put("POST/admin/setUserBan", new SetUserBanAction());
    }
    public Action getAction(WebContext webContext) {
        return actions.get(webContext.getRequestedAction());
    }
}


