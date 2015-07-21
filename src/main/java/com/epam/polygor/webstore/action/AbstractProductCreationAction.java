package com.epam.polygor.webstore.action;

import com.epam.polygor.webstore.servlet.Scope;
import com.epam.polygor.webstore.servlet.WebContext;

public abstract class AbstractProductCreationAction implements Action {
protected static final String CATEGORY_NAME = "categoryName";
    protected static final String PRODUCT_NAME = "productName";
    protected static final String PRODUCT_MANUFACTURER = "productManufacturer";
    protected static final String PRICE = "price";
    protected static final String DESCRIPTION = "description";
    protected static final String IMAGE = "image";
    @Override
    public abstract ActionResult execute(WebContext webContext);

    protected void setAttributesToFlashScope(WebContext webContext) {
        webContext.setAttribute(CATEGORY_NAME, webContext.getParameter(CATEGORY_NAME), Scope.FLASH);
        webContext.setAttribute(PRODUCT_NAME, webContext.getParameter(PRODUCT_NAME), Scope.FLASH);
        webContext.setAttribute(PRODUCT_MANUFACTURER, webContext.getParameter(PRODUCT_MANUFACTURER), Scope.FLASH);
        webContext.setAttribute(PRICE, webContext.getParameter(PRICE), Scope.FLASH);
        webContext.setAttribute(DESCRIPTION, webContext.getParameter(DESCRIPTION), Scope.FLASH);
    }
}
