package com.epam.polygor.webstore.listener;
import com.epam.polygor.webstore.service.*;
import com.epam.polygor.webstore.util.DatabaseCheckUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.CopyOnWriteArrayList;
@WebListener
public class ContextListener implements ServletContextListener {
    public static final String CATEGORIES_LIST = "categories";
    private DatabaseCheckUtil databaseCheckUtil = new DatabaseCheckUtil();
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        if (!databaseCheckUtil.check()) {
            databaseCheckUtil.create();
        }
        CategoryService categoryService = new CategoryService();
        servletContext.setAttribute(CATEGORIES_LIST, new CopyOnWriteArrayList<>(categoryService.getCategories()));

    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
