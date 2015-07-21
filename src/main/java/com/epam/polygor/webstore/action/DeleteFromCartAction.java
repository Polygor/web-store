package com.epam.polygor.webstore.action;


import com.epam.polygor.webstore.listener.SessionListener;
import com.epam.polygor.webstore.model.Cart;
import com.epam.polygor.webstore.servlet.Scope;
import com.epam.polygor.webstore.servlet.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DeleteFromCartAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(DeleteFromCartAction.class);

    @Override
    public ActionResult execute(WebContext webContext) {
        Cart cart = (Cart) webContext.getAttribute(SessionListener.CART_ATTRIBUTE, Scope.SESSION);
        String[] productToDelete = webContext.getParameterValues("productIdToDelete");
        if (productToDelete != null) {
            for (String productID : productToDelete) {
                log.debug("Selected product ID to delete: " + productID);
                Long id = Long.valueOf(productID);
                cart.removeProduct(id);
            }
        }
        return new ActionResult(webContext.getPreviousURI(), true);
    }
}
