package com.epam.polygor.webstore.listener;

import com.epam.polygor.webstore.model.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
@WebListener
public class SessionListener implements HttpSessionListener {
    private static final Logger log = LoggerFactory.getLogger(SessionListener.class);
    public static final String CART_ATTRIBUTE = "cart";

    public void sessionCreated(HttpSessionEvent se) {
        log.debug("creating session with id: " + se.getSession().getId());
        se.getSession().setAttribute(CART_ATTRIBUTE, new Cart());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.debug("destroying session with id: " + se.getSession().getId());
    }
}