package com.epam.polygor.webstore.dao;

public interface DaoFactory {
    public abstract DaoManager getDaoManager() throws DaoException;
}
