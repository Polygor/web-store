package com.epam.polygor.webstore.action;

import com.epam.polygor.webstore.listener.ContextListener;
import com.epam.polygor.webstore.model.Category;
import com.epam.polygor.webstore.service.CategoryService;
import com.epam.polygor.webstore.servlet.Scope;
import com.epam.polygor.webstore.servlet.WebContext;

import java.util.List;


public class DeleteCategoryAction implements Action {
    public static final String CATEGORY_NAME = "categoryName";

    @Override
    @SuppressWarnings("unchecked")
    public ActionResult execute(WebContext webContext) {
        CategoryService categoryService = new CategoryService();
        String categoryName = webContext.getParameter(CATEGORY_NAME);
        if (categoryName != null && !categoryName.isEmpty()) {
            Category category = categoryService.getCategoryByName(categoryName);
            if (category != null) {
                categoryService.deleteCategory(category);
                //remove from app context
                List<Category> categories = (List<Category>)
                        webContext.getAttribute(ContextListener.CATEGORIES_LIST, Scope.APPLICATION);
                categories.remove(category);
            }
        }
        return new ActionResult(webContext.getPreviousURI(), true);
    }
}

