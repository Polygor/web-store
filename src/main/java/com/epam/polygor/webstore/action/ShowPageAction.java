package com.epam.polygor.webstore.action;

import com.epam.polygor.webstore.servlet.WebContext;


public class ShowPageAction implements Action {
    private ActionResult actionResult;

    @Override
    public ActionResult execute(WebContext webContext) {
        return actionResult;
    }

    public ShowPageAction(String page) {
        actionResult = new ActionResult(page);
    }
}



