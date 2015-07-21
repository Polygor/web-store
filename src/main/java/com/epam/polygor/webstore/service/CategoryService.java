package com.epam.polygor.webstore.service;

import com.epam.polygor.webstore.dao.CategoryDao;
import com.epam.polygor.webstore.dao.DaoFactory;
import com.epam.polygor.webstore.dao.DaoManager;
import com.epam.polygor.webstore.model.Category;

import java.util.List;

public class CategoryService extends AbstractService {

    public CategoryService(){}

    public List<Category> getCategories() {
        List<Category> categories;
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            CategoryDao categoryDao = daoManager.getCategoryDao();
            categories = categoryDao.getCategoryList();
            return categories;
        }
    }


    public Category createCategory(String name) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            CategoryDao categoryDao = daoManager.getCategoryDao();
            //checking if database has the same category
            Category categoryFromDatabase = getCategoryByName(name);
            if(categoryFromDatabase!= null) return null;
            Category category = new Category(name);
            category.setName(name);
            return categoryDao.insert(category);
        }
    }

    public Category getCategory(long categoryId) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            CategoryDao categoryDao = daoManager.getCategoryDao();
            return categoryDao.findById(categoryId);
        }
    }


    public void deleteCategory(Category category) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            CategoryDao categoryDao = daoManager.getCategoryDao();
            categoryDao.deleteById(category);
        }
    }

    public boolean categoryAlreadyExist(String name) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            CategoryDao categoryDao = daoManager.getCategoryDao();
            return categoryDao.checkIfCategoryExist(name);
        }
    }

    public Category getCategoryByName(String name) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            CategoryDao categoryDao = daoManager.getCategoryDao();
            return categoryDao.findByCategoryName(name);
        }
    }
}


