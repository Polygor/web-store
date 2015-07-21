package com.epam.polygor.webstore.dao;


public interface Dao<T> {
    public T insert(T entity) throws DaoException;

    public T findById(long id) throws DaoException;

    public boolean update(T entity) throws DaoException;

    public boolean deleteById(T entity) throws DaoException;
}