package com.epam.polygor.webstore.action;

import com.epam.polygor.webstore.model.Product;
import com.epam.polygor.webstore.service.ProductService;
import com.epam.polygor.webstore.servlet.Scope;
import com.epam.polygor.webstore.servlet.WebContext;

public class ShowProductDetailsAction implements Action {
    public static final String PRODUCT_ID = "product_id";
    private ActionResult actionResult = new ActionResult("details");
    ProductService productService = new ProductService();

    @Override
    public ActionResult execute(WebContext webContext) {
        String id = webContext.getParameter(PRODUCT_ID);
        if (id != null) {
            Product product = productService.getProductByID(Long.valueOf(id));
            webContext.setAttribute("product", product, Scope.REQUEST);
        }
        return actionResult;
    }
}