package com.epam.polygor.webstore.dao;

import com.epam.polygor.webstore.model.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;



public abstract class AbstractJDBCDao<T extends BaseEntity> {
    protected static final String ID = "ID";
    protected static final String CATEGORY_ID = "CATEGORY_ID";
    protected static final String IMAGE_ID = "IMAGE_ID";
    protected static final String PRICE = "PRICE";
    protected static final String PRODUCTS_ID = "PRODUCTS_ID";
    protected static final String PRODUCT_ID = "PRODUCT_ID";
    protected static final String PURCHASES_ID = "PURCHASES_ID";
    protected static final String STATUS_ID = "STATUS_ID";
    protected static final String USER_ID = "USER_ID";
    protected static final String DATE = "DATE";
    protected static final String NAME = "NAME";
    protected static final String CATEGORY_NAME = "NAME";
    protected static final String BLOCKED = ("BLOCKED");
    protected static final String FIRST_NAME = "FIRSTNAME";
    protected static final String LAST_NAME = "LASTNAME";
    protected static final String BIRTH = "BIRTH";
    protected static final String PHONE = "PHONE";
    protected static final String LOGIN = "LOGIN";
    protected static final String PASSWORD = "PASSWORD";
    protected static final String EMAIL = "EMAIL";
    protected static final String CITY = "CITY";
    protected static final String COUNTRY = "COUNTRY";
    protected static final String ADDRESS = "ADDRESS";
    protected static final String POSTCODE = "POSTCODE";
    protected static final String IS_BANNED = "BANNED";
    protected static final String ROLE_NAME = "ROLE.NAME";
    private static final Logger log = LoggerFactory.getLogger(AbstractJDBCDao.class);
    protected Connection connection;

    public AbstractJDBCDao(Connection connection) {this.connection = connection;} {
    }

    public abstract T createEntity(ResultSet resultSet) throws DaoException;

    public abstract T getFromResultSet(ResultSet resultSet) throws DaoException;

}

