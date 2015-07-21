package com.epam.polygor.webstore.action;

import com.epam.polygor.webstore.listener.ContextListener;
import com.epam.polygor.webstore.model.Category;
import com.epam.polygor.webstore.service.CategoryService;
import com.epam.polygor.webstore.servlet.Scope;
import com.epam.polygor.webstore.servlet.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ResourceBundle;


public class CreateCategoryAction implements Action {
    public static final Logger log = LoggerFactory.getLogger(CreateProductAction.class);
    private static final String CATEGORY = "name";

    @SuppressWarnings("unchecked")
    @Override
    public ActionResult execute(WebContext webContext) {
        ResourceBundle messagesBundle = webContext.getMessagesBundle();
        CategoryService categoryService = new CategoryService();
        ActionResult backToPreviousPage = new ActionResult(webContext.getPreviousURI(), true);
        String name = webContext.getParameter(CATEGORY);
        if (name == null || name.isEmpty()) {
            webContext.setAttribute("errorMessage", messagesBundle.getString("add-category.message.categoryIsEmpty"), Scope.FLASH);
            return backToPreviousPage;
        }
        Category createdCategory = categoryService.createCategory(name);
        //add new category to the list in app context
        if (createdCategory != null) {
            List<Category> categories = (List<Category>)
                    webContext.getAttribute(ContextListener.CATEGORIES_LIST, Scope.APPLICATION);
            categories.add(createdCategory);
            webContext.setAttribute
                    ("successMessage", messagesBundle.getString("add-category.message.success"), Scope.FLASH);
        } else {
            webContext.setAttribute
                    ("errorMessage", messagesBundle.getString("add-category.message.exist"), Scope.FLASH);
        }
        return backToPreviousPage;
    }
}
