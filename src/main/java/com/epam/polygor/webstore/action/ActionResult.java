package com.epam.polygor.webstore.action;

public class ActionResult {
    private String page;
    private boolean redirect;

    public ActionResult(String view) {
        this.page = view;
    }

    public ActionResult(String view, boolean redirect) {
        this.page = view;
        this.redirect = redirect;
    }

    public String getPageName() {
        return page;
    }

    public boolean isRedirect() {
        return redirect;
    }
}

