package com.epam.polygor.webstore.dao;

import java.sql.Connection;

public interface DaoManager extends AutoCloseable  {
    Connection getConnection() throws DaoException;

    ProductDao getProductDao() throws DaoException;

    ImageDao getImageDao() throws DaoException;

    UserDao getUserDao() throws DaoException;

    PurchaseDao getPurchaseDao() throws  DaoException;

    CategoryDao getCategoryDao() throws DaoException;

    StatusDao getStatusDao() throws DaoException;

    public void beginTransaction() throws DaoException;

    public void closeTransaction() throws DaoException;

    public void close() throws DaoException;
}
