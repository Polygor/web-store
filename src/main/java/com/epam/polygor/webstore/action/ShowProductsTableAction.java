package com.epam.polygor.webstore.action;

import com.epam.polygor.webstore.model.Product;
import com.epam.polygor.webstore.servlet.Scope;
import com.epam.polygor.webstore.servlet.WebContext;

import java.util.List;

public class ShowProductsTableAction extends AbstractShowProductsAction {
    private ActionResult adminPage = new ActionResult("product-management");
    private static final String CATEGORY_NAME = "name";

    @Override
    public ActionResult execute(WebContext webContext) {
        String categoryName = null;
        try {
            categoryName = super.getCategoryNameFromPath(webContext);
        } catch (ActionException e) {
            e.printStackTrace();
        }
        if (categoryName != null) {
            List<Product> products = super.getProducts(categoryName);
            webContext.setAttribute("products", products, Scope.REQUEST);
            webContext.setAttribute("categoryName", categoryName, Scope.REQUEST);
        }
        return adminPage;
    }
}