package com.epam.polygor.webstore.dao.implementations;

import com.epam.polygor.webstore.dao.*;
import com.epam.polygor.webstore.model.Product;
import com.epam.polygor.webstore.model.Purchase;
import com.epam.polygor.webstore.model.Status;
import com.epam.polygor.webstore.model.User;
import com.epam.polygor.webstore.util.DaoCloser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SQLPurchaseDao extends AbstractJDBCDao<Purchase> implements PurchaseDao {
    private static final String JOIN = "LEFT OUTER JOIN PRODUCTS ON PURCHASES.PRODUCT_ID = PRODUCT.PRODUCT_ID  " +
            " RIGHT OUTER JOIN USERS ON PURCHASES.USER_ID = USERS.ID" +
            " INNER JOIN STATUS ON STATUS.STATUS_ID = STATUS.STATUS_ID";
    private static final String CREATE_PURCHASE = "INSERT INTO PURCHASES (PURCHASES_ID, PRODUCT_ID, STATUS_ID, USERS_ID, DATE, PRICE) VALUES (default, (select PRODUCT_ID from PRODUCTS WHERE PRODUCTS.PRODUCT_ID = ?), (select STATUS_ID from STATUS where STATUS.STATUS_ID = ?) , (SELECT ID FROM USERS WHERE USERS.ID = ?), ?, ?)";
    private static final String UPDATE_PURCHASE = "UPDATE  PURCHASES" +
                       " SET STATUS_ID = ?, DATE = ?, PRICE = ?";
    private static final String GET_PURCHASE_LIST = "SELECT PURCHASES_ID, PRODUCT_ID, STATUS_ID, USERS_ID, DATE, PRICE FROM PURCHASES" ;
    private static final String FIND_BY_ID = "SELECT FROM PURCHASES WHERE PURCHASES_ID =?" + JOIN;
    private static final String DELETE_BY_ID = "DELETE FROM PURCHASES  WHERE PURCHASES_ID = ?" + JOIN;
    private static final Logger log = LoggerFactory.getLogger(SQLPurchaseDao.class);


    public SQLPurchaseDao(Connection connection) {
        super(connection);
        this.connection = connection;
    }

    @Override
    public Purchase createEntity(ResultSet resultSet) throws DaoException {
        StatusDao statusDao = new SQLStatusDao(connection);
        UserDao userDao = new SQLUserDao(connection);
        ProductDao productDao = new SQLProductDao(connection);
        try {
            Status status = statusDao.findById(resultSet.getLong(STATUS_ID));
            Timestamp date = resultSet.getTimestamp(DATE);
            Product product = productDao.findById(resultSet.getLong(PRODUCTS_ID));
            User user = userDao.findById(resultSet.getLong(USER_ID));
            Long id = resultSet.getLong(PURCHASES_ID);
            BigDecimal price = resultSet.getBigDecimal(PRICE);
            return (Purchase) new Purchase()
                    .setProduct(product)
                    .setUser(user)
                    .setDate(date)
                    .setStatus(status)
                    .setPrice(price)
                    .setId(id);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Purchase getFromResultSet(ResultSet resultSet) throws DaoException {
        StatusDao statusDao = new SQLStatusDao(connection);
        UserDao userDao = new SQLUserDao(connection);
        ProductDao productDao = new SQLProductDao(connection);
        Purchase purchase = null;
        try {
            if (resultSet.next()) {
                purchase = new Purchase();
                purchase.setId(resultSet.getLong(PURCHASES_ID));
                purchase.setProduct(productDao.findById(resultSet.getLong(PRODUCTS_ID)));
                purchase.setDate(resultSet.getTimestamp(DATE));
                purchase.setStatus(statusDao.findById(resultSet.getLong(STATUS_ID)));
                purchase.setUser(userDao.findById(resultSet.getLong(USER_ID)));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return purchase;
    }

    @Override
    public Purchase insert(Purchase purchase) throws DaoException {
        try (PreparedStatement purchaseStmt = connection.prepareStatement(CREATE_PURCHASE)) {
            purchaseStmt.setLong(1, purchase.getProduct().getId());
            purchaseStmt.setLong(2, purchase.getUser().getId());
            purchaseStmt.setTimestamp(3, purchase.getDate());
            purchaseStmt.setLong(4, purchase.getStatus().getId());
            purchaseStmt.setBigDecimal(5, purchase.getPrice());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return purchase;
    }

    @Override
    public Purchase findById(long id) throws DaoException {
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
    public boolean update(Purchase purchase) throws DaoException {
        try (PreparedStatement updatePurchaseStmt = connection.prepareStatement(UPDATE_PURCHASE)) {
            updatePurchaseStmt.setLong(1, purchase.getStatus().getId());
            updatePurchaseStmt.setTimestamp(2, purchase.getDate());
            updatePurchaseStmt.setBigDecimal(3, purchase.getPrice());
            updatePurchaseStmt.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return true;
    }



    @Override
    public boolean deleteById(Purchase purchase) throws DaoException {
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
                preparedStatement.setLong(1, purchase.getId());
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
            return true;
        }


    @Override
    public List<Purchase> getPurchaseList() throws DaoException {
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_PURCHASE_LIST)) {
            List<Purchase> purchases = new ArrayList<>();
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                purchases.add(createEntity(resultSet));
            }
            return purchases;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoCloser.closeResultSet(resultSet);
        }
    }
}


