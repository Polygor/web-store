package com.epam.polygor.webstore.dao.implementations;

import com.epam.polygor.webstore.dao.DaoException;
import com.epam.polygor.webstore.dao.DaoFactory;
import com.epam.polygor.webstore.dao.DaoManager;
import com.epam.polygor.webstore.util.PropertyManagerForPool;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.pool.HikariPool;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLDaoFactory implements DaoFactory {
    private static final String PROPERTIES_FILE = "database.properties";
    private static PropertyManagerForPool propertyManagerForPool = new PropertyManagerForPool(PROPERTIES_FILE);
    private static final String URL = "db.url";
    private static final String USERNAME = "db.user";
    private static final String PASSWORD = "db.password";
    private static final String DRIVER = "db.driver";
    private final HikariPool connectionPool;

    public SQLDaoFactory() throws DaoException {
        try {
            Class.forName(propertyManagerForPool.getProperty(DRIVER));
        } catch (ClassNotFoundException e) {
            throw new DaoException(e);
        }
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(propertyManagerForPool.getProperty(URL));
        config.setUsername(propertyManagerForPool.getProperty(USERNAME));
        config.setPassword(propertyManagerForPool.getProperty(PASSWORD));
        connectionPool = new HikariPool(config);
    }
    public static SQLDaoFactory getInstance() {
        return InstanceHolder.instance;
    }

    public DaoManager getDaoManager() throws DaoException {
        Connection connection;
        try {
            connection = connectionPool.getConnection();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return new SQLDaoManager(connection);
    }

    private static class InstanceHolder {
        private static SQLDaoFactory instance = new SQLDaoFactory();
    }
}