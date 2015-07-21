package com.epam.polygor.webstore.action;

import com.epam.polygor.webstore.model.Product;
import com.epam.polygor.webstore.servlet.Scope;
import com.epam.polygor.webstore.servlet.WebContext;

import java.util.List;


public class ShowCatalogPageAction extends AbstractShowProductsAction {
    private ActionResult catalogPage = new ActionResult("catalog");

    @Override
    public ActionResult execute(WebContext webContext) {
        String categoryName = super.getCategoryNameFromPath(webContext);
        if (categoryName != null) {
            List<Product> products = super.getProducts(categoryName);
            webContext.setAttribute("products", products, Scope.REQUEST);
        } else {
            List<Product> products = super.getAllProducts();
            webContext.setAttribute("products", products, Scope.REQUEST);
        }
        return catalogPage;
    }
}
