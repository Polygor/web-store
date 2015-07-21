package com.epam.polygor.webstore.action;

import com.epam.polygor.webstore.servlet.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShowProductCreationPageAction extends AbstractProductCreationAction {
    private static final Logger log = LoggerFactory.getLogger(ShowProductCreationPageAction.class);
    ActionResult result = new ActionResult("product-creation");

    @Override
    public ActionResult execute(WebContext webContext) {
        super.setAttributesToFlashScope(webContext);
        return result;
    }
}
