package com.epam.polygor.webstore.action;

import com.epam.polygor.webstore.servlet.WebContext;

public interface Action {
    public ActionResult execute(WebContext webContext);
}
