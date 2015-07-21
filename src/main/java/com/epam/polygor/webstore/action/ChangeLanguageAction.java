package com.epam.polygor.webstore.action;

import com.epam.polygor.webstore.servlet.WebContext;

import java.util.Locale;


public class ChangeLanguageAction implements Action {
    private static final String EN_LANGUAGE = "en";
    private static final String RU_LANGUAGE = "ru";

    @Override
    public ActionResult execute(WebContext webContext) {
        String lang = webContext.getParameter("lang");
        if (lang != null) {
            Locale selectedLocale = null;
            if (lang.equals(EN_LANGUAGE)) selectedLocale = Locale.ENGLISH;
            else if (lang.equals(RU_LANGUAGE)) selectedLocale = new Locale("ru", "RU");
            if (selectedLocale != null) {
                webContext.addLangCookie(selectedLocale);
                webContext.setLocale(selectedLocale);
            }
        }
        return new ActionResult(webContext.getPreviousURI(), true);
    }
}
