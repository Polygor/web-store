package com.epam.polygor.webstore.dao.implementations;

import com.epam.polygor.webstore.dao.AbstractJDBCDao;
import com.epam.polygor.webstore.dao.CategoryDao;
import com.epam.polygor.webstore.dao.DaoException;
import com.epam.polygor.webstore.model.Category;
import com.epam.polygor.webstore.util.DaoCloser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLCategoryDao extends AbstractJDBCDao<Category> implements CategoryDao {
    private static final String CREATE_CATEGORY = "INSERT INTO CATEGORY (ID, NAME) VALUES (default, ?)";
    private static final String DELETE_BY_ID = "DELETE FROM CATEGORY  WHERE ID = ?";
    private static final String UPDATE = "UPDATE CATEGORY SET CATEGORY_NAME = ?";
    private static final String FIND_BY_ID = "SELECT * FROM CATEGORY WHERE ID=?";
    private static final String FIND_CATEGORY_NAME = "SELECT id, name FROM category WHERE category.name = ?";
    private static final String GET_CATEGORY_LIST = "SELECT NAME, ID FROM CATEGORY";
    private static final String CHECK_CATEGORY_NAME = "SELECT * FROM CATEGORY WHERE CATEGORY.NAME = ?";
    private static final Logger log = LoggerFactory.getLogger(SQLCategoryDao.class);
    public SQLCategoryDao(Connection connection) {
        super(connection);
    }



    @Override
    public Category createEntity(ResultSet resultSet) throws DaoException {
        try {
            long id = resultSet.getLong(ID);
            String name = resultSet.getString(NAME);
            return (Category) new Category()
                    .setName(name)
                    .setId(id);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public Category getFromResultSet(ResultSet resultSet) throws DaoException {
        Category category = null;
        try {
            if (resultSet.next()) {
                category = new Category();
                category.setId(resultSet.getLong(ID));
                category.setName(resultSet.getString(CATEGORY_NAME));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return category;
    }


    @Override
    public Category insert(Category category) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CATEGORY)) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return category;
    }


    @Override
    public Category findById(long id) throws DaoException {
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            return getFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoCloser.closeResultSet(resultSet);
        }
    }


    @Override
    public boolean update(Category category) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return true;
    }

    @Override
    public boolean deleteById(Category category) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, category.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return true;
    }


    @Override
    public Category findByCategoryName(String name) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_CATEGORY_NAME)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return getFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }



    @Override
    public List<Category> getCategoryList() throws DaoException {
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_CATEGORY_LIST)) {
            List<Category> categories = new ArrayList<>();
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                categories.add(createEntity(resultSet));
            }
            return categories;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoCloser.closeResultSet(resultSet);
        }
    }

    @Override
    public boolean checkIfCategoryExist(String name) throws DaoException {
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_CATEGORY_NAME)) {
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoCloser.closeResultSet(resultSet);
        }
    }
    }

