package com.epam.polygor.webstore.action;

import com.epam.polygor.webstore.listener.SessionListener;
import com.epam.polygor.webstore.model.Cart;
import com.epam.polygor.webstore.model.Product;
import com.epam.polygor.webstore.service.ProductService;
import com.epam.polygor.webstore.servlet.Scope;
import com.epam.polygor.webstore.servlet.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddToCartAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(AddToCartAction.class);

    @Override
    public ActionResult execute(WebContext webContext) {
        ProductService productService = new ProductService();
        Cart cart = (Cart) webContext.getAttribute(SessionListener.CART_ATTRIBUTE, Scope.SESSION);
        Long id = Long.valueOf(webContext.getParameter("id"));
        if (id != null) {

            log.debug("Product id to add to cart: " + id);
            Product product = productService.getProductByID(id);
            if (product != null) {
                cart.addProduct(product);
                log.debug("Added to cart: " + product.getName());
                log.debug("Cart size: " + cart.getProductAmount());
            }
        }
        return new ActionResult(webContext.getPreviousURI(), true);
    }
}