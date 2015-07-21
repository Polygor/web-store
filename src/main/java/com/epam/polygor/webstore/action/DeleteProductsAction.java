package com.epam.polygor.webstore.action;


import com.epam.polygor.webstore.service.ProductService;
import com.epam.polygor.webstore.servlet.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteProductsAction implements Action {
    public static final Logger LOGGER = LoggerFactory.getLogger(DeleteProductsAction.class);

    @Override
    public ActionResult execute(WebContext webContext) {
        ProductService productService = new ProductService();
        String[] productToDelete = webContext.getParameterValues("productIdToDelete");
        if (productToDelete != null) {
            for (String productID : productToDelete) {
                Long id = Long.valueOf(productID);
                productService.deleteProduct(id);
            }
        }
        return new ActionResult(webContext.getPreviousURI(), true);
    }
}