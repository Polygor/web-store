package com.epam.polygor.webstore.dao;

import com.epam.polygor.webstore.model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductDao extends Dao<Product> {
    Product findByName(String name) throws DaoException;

    Product findByManufacturer(String manufacturer) throws DaoException;

    Product findByDescription (String description) throws DaoException;

    Product findByCategory(String category) throws DaoException;

    void deleteByName(Product product) throws DaoException;

    void deleteByManufacturer(Product product) throws DaoException;

    void deleteByDescription (Product product) throws DaoException;

    List<Product> getProductList() throws DaoException;
}