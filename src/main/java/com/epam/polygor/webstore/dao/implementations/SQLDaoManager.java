package com.epam.polygor.webstore.dao.implementations;

import com.epam.polygor.webstore.dao.*;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLDaoManager implements DaoManager {
    private Connection connection;

    public SQLDaoManager(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Connection getConnection() {
        return connection;
    }
    public UserDao getUserDao() {
        return new SQLUserDao(connection);
    }
    public ProductDao getProductDao() {
        return new SQLProductDao(connection);
    }
    public ImageDao getImageDao() {return new SQLImageDao(connection);}
    public PurchaseDao getPurchaseDao() {return new SQLPurchaseDao(connection);}
    public StatusDao getStatusDao(){return new SQLStatusDao(connection);}
    public CategoryDao getCategoryDao() {return new SQLCategoryDao(connection);}

    @Override
    public void beginTransaction() throws DaoException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void closeTransaction() throws DaoException {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void close() throws DaoException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}