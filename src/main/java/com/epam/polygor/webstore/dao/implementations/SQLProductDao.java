package com.epam.polygor.webstore.dao.implementations;

import com.epam.polygor.webstore.dao.*;
import com.epam.polygor.webstore.model.Category;
import com.epam.polygor.webstore.model.Image;
import com.epam.polygor.webstore.model.Product;
import com.epam.polygor.webstore.util.DaoCloser;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLProductDao extends AbstractJDBCDao<Product> implements ProductDao {
    private static final String JOIN = " INNER JOIN category ON products.category_id = category.id" +
            " LEFT OUTER JOIN image ON products.image_id = image.image_id";
    private static final String FIND_BY_ID = "SELECT * FROM PRODUCTS "+ JOIN +" WHERE PRODUCT_ID=?";
    private static final String FIND_BY_NAME = "SELECT * FROM PRODUCTS " + JOIN + " WHERE PRODUCT_NAME=?";
    private static final String FIND_BY_MANUFACTURER = "SELECT * FROM PRODUCTS" + JOIN + " WHERE MANUFACTURER=?";
    private static final String FIND_BY_CATEGORY = "SELECT * FROM PRODUCTS" + JOIN + " AND category.name= ?";
    private static final String FIND_BY_DESCRIPTION = "SELECT * FROM PRODUCTS" + JOIN + " WHERE DESCRIPTION =?";
    private static final String DELETE_BY_ID = "DELETE FROM PRODUCTS  WHERE PRODUCT_ID = ?";
    private static final String DELETE_BY_NAME = "DELETE FROM PRODUCTS WHERE PRODUCT_NAME =?";
    private static final String DELETE_BY_MANUFACTURER = "DELETE FROM PRODUCTS WHERE MANUFACTURER=?";
    private static final String DELETE_BY_DESCRIPTION = "DELETE FROM PRODUCTS WHERE DESCRIPTION = ?";
    private static final String UPDATE = "UPDATE  PRODUCTS" +
            " SET NAME = ?, MANUFACTURER = ?, CATEGORY_ID = ?, DESCRIPTION = ?, IMAGE_ID = ?, PRICE = ?, IS_AVAILABLE = ?";
    private static final String GET_PRODUCT_LIST = "SELECT PRODUCT_ID, PRODUCT_NAME, MANUFACTURER, CATEGORY_ID, DESCRIPTION, IMAGE_ID, PRICE, IS_AVAILABLE FROM PRODUCTS ";
    private static final String CREATE_PRODUCT = "INSERT INTO PRODUCTS (PRODUCT_ID, PRODUCT_NAME, MANUFACTURER, CATEGORY_ID, DESCRIPTION, IMAGE_ID, PRICE, IS_AVAILABLE) VALUES (default, ?, ?,(SELECT ID FROM CATEGORY WHERE CATEGORY.ID = ?), ?,(SELECT MAX (IMAGE_ID) FROM IMAGE), ?, true)";
    private static final String PRODUCT_NAME = "PRODUCT_NAME";
    private static final String MANUFACTURER = "manufacturer";
    private static final String DESCRIPTION = "description";
    private static final String IS_AVAILABLE = "IS_AVAILABLE";

    public SQLProductDao(Connection connection) {
        super(connection);
        this.connection = connection;
    }


    @Override
    public Product insert(Product product) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_PRODUCT)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getManufacturer());
            preparedStatement.setLong(3, product.getCategory().getId());
            preparedStatement.setString(4, product.getDescription());
            preparedStatement.setBigDecimal(5, product.getPrice());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return product;
    }

    @Override
    public Product findById(long id) throws DaoException {
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
    public Product findByName(String name) throws DaoException {
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            return getFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoCloser.closeResultSet(resultSet);
        }
    }

    @Override
    public Product findByManufacturer(String manufacturer) throws DaoException {
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_MANUFACTURER)) {
            preparedStatement.setString(1, manufacturer);
            resultSet = preparedStatement.executeQuery();
            return getFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoCloser.closeResultSet(resultSet);
        }
    }


    @Override
    public Product findByDescription(String description) throws DaoException {
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_DESCRIPTION)) {
            preparedStatement.setString(1, description);
            resultSet = preparedStatement.executeQuery();
            return getFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoCloser.closeResultSet(resultSet);
        }
    }

    @Override
    public Product findByCategory(String category) throws DaoException {
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_CATEGORY)) {
            preparedStatement.setString(1, category);
            resultSet = preparedStatement.executeQuery();
            return getFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoCloser.closeResultSet(resultSet);
        }
    }

    @Override
    public boolean update(Product product) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getManufacturer());
            preparedStatement.setLong(3, product.getCategory().getId());
            preparedStatement.setString(4, product.getDescription());
            preparedStatement.setBigDecimal(5, product.getPrice());
            if (product.getImage() == null) {
                preparedStatement.setString(6, null);
            } else {
                preparedStatement.setLong(6, product.getImage().getId());
            }
            preparedStatement.setBoolean(7, product.isInStock());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return true;
    }

    @Override
    public boolean deleteById(Product product) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, product.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return true;
    }

    @Override
    public void deleteByName(Product product) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_NAME)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteByManufacturer(Product product) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_MANUFACTURER)) {
            preparedStatement.setString(1, product.getManufacturer());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public void deleteByDescription(Product product) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_DESCRIPTION)) {
            preparedStatement.setString(1, product.getDescription());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Product getFromResultSet(ResultSet resultSet) throws DaoException {
        ImageDao imageDao = new SQLImageDao(connection);
        CategoryDao categoryDao = new SQLCategoryDao(connection);

        Product product = null;
        try {
            if (resultSet.next()) {
                product = new Product();
                product.setId(resultSet.getLong(PRODUCT_ID));
                product.setName(resultSet.getString(PRODUCT_NAME));
                product.setManufacturer(resultSet.getString(MANUFACTURER));
                product.setCategory(categoryDao.findById(resultSet.getLong(CATEGORY_ID)));
                product.setDescription(resultSet.getString(DESCRIPTION));
                product.setImage(imageDao.findById(resultSet.getLong(IMAGE_ID)));
                product.setPrice(resultSet.getBigDecimal(PRICE));

            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return product;
    }

    @Override
    public List<Product> getProductList() throws DaoException {
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_LIST)) {
            List<Product> products = new ArrayList<Product>();
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                products.add(createEntity(resultSet));
            }
            return products;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoCloser.closeResultSet(resultSet);
        }
    }

    @Override
    public Product createEntity(ResultSet resultSet) throws DaoException {
        ImageDao imageDao = new SQLImageDao(connection);
        CategoryDao categoryDao = new SQLCategoryDao(connection);
        try {
            Category category = categoryDao.findById(resultSet.getLong(CATEGORY_ID));
            long id = resultSet.getLong(PRODUCT_ID);
            String name = resultSet.getString(PRODUCT_NAME);
            String manufacturer = resultSet.getString(MANUFACTURER);
            String description = resultSet.getString(DESCRIPTION);
            BigDecimal price = resultSet.getBigDecimal(PRICE);
            boolean inStock = resultSet.getBoolean(IS_AVAILABLE);
            Image image = imageDao.findById(resultSet.getLong(IMAGE_ID));
            return (Product) new Product()
                    .setName(name)
                    .setDescription(description)
                    .setManufacturer(manufacturer)
                    .setImage(image)
                    .setCategory(category)
                    .setPrice(price)
                    .setInStock(inStock)
                    .setId(id);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}

