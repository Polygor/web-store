package com.epam.polygor.webstore.dao;


import com.epam.polygor.webstore.model.Category;

import java.util.List;

public interface CategoryDao extends Dao<Category> {
    Category findByCategoryName (String name) throws DaoException;
    List<Category> getCategoryList() throws DaoException;
    boolean checkIfCategoryExist( String name) throws DaoException;
}
