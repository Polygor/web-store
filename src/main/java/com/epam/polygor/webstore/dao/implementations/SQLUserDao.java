package com.epam.polygor.webstore.dao.implementations;

import com.epam.polygor.webstore.util.DaoCloser;
import com.epam.polygor.webstore.dao.DaoException;
import com.epam.polygor.webstore.dao.AbstractJDBCDao;
import com.epam.polygor.webstore.dao.UserDao;
import com.epam.polygor.webstore.model.User;
import com.ibatis.common.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLUserDao extends AbstractJDBCDao<User> implements UserDao {
    private static final String JOIN = " INNER JOIN ROLE ON USERS.ROLE_ID = ROLE.ID";
    private static final String FIND_BY_USER_ID = "SELECT * FROM USERS " + JOIN +"  WHERE USERS.ID = ?";
    private static final String FIND_BY_LOGIN = "SELECT * FROM USERS  WHERE LOGIN =?";
    private static final String FIND_BY_LASTNAME = "SELECT * FROM USERS  WHERE LASTNAME =?";
    private static final String FIND_BY_EMAIL = "SELECT * FROM USERS  WHERE EMAIL=?";
    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD = " SELECT * FROM USERS " + JOIN + "  WHERE LOGIN = ? and PASSWORD = ?";
    private static final String GET_USER_LIST = "SELECT * FROM USERS" + JOIN;
    private static final String DELETE_BY_ID = "UPDATE USERS SET BANNED = 'TRUE' WHERE ID = ?";
    private static final String DELETE_BY_EMAIL = "DELETE FROM USERS WHERE EMAIL=?";
    private static final String DELETE_BY_LASTNAME = "DELETE FROM USERS WHERE LASTNAME= ?";
    private static final String UPDATE = "UPDATE USERS " + " SET FIRSTNAME = ?, LASTNAME = ?, BIRTH = ?, PHONE = ?," +
            "LOGIN = ?, PASSWORD =?, EMAIL = ?, CITY = ?, COUNTRY = ?, ADDRESS =?, POSTCODE = ?, BANNED =?, ROLE_ID = (select id from ROLE where ROLE.name = ?)";
    private static final String INSERT_USER = " INSERT INTO USERS(ID, FIRSTNAME, LASTNAME, BIRTH, PHONE, LOGIN, PASSWORD, EMAIL, CITY, COUNTRY, ADDRESS, POSTCODE, BANNED, ROLE_ID) VALUES (default, ?, ?, ?, ?, ?, ? ,? ,? ,? ,? ,? , false,(select id from ROLE where ROLE.name = ?))";
    private static final String UPDATE_PASSWORD = "UPDATE USERS SET PASSWORD = ? WHERE ID = ?";
    private static final String FIND_NOT_BANNED_USERS = "SELECT FROM USERS WHERE BANNED = 'FALSE'";

    private static final Logger log = LoggerFactory.getLogger(SQLUserDao.class);
    public SQLUserDao(Connection connection) {
        super(connection);
        this.connection = connection;
    }
    @Override

    public User insert(User user) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDate(3, user.getBirth());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.setString(5, user.getLogin());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.setString(7, user.getEmail());
            preparedStatement.setString(8, user.getCity());
            preparedStatement.setString(9, user.getCountry());
            preparedStatement.setString(10, user.getAddress());
            preparedStatement.setString(11, user.getPostcode());
            preparedStatement.setString(12, user.getRole().name());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return user;
    }

    @Override
    public boolean update(User user) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDate(3, user.getBirth());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getLogin());
            preparedStatement.setString(7, user.getPassword());
            preparedStatement.setString(8, user.getCity());
            preparedStatement.setString(9, user.getCountry());
            preparedStatement.setString(10, user.getAddress());
            preparedStatement.setString(11, user.getPostcode());
            preparedStatement.setBoolean(12, user.isBanned());
            preparedStatement.setString(13, user.getRole().name());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return true;
    }

    @Override
    public User findById(long id) throws DaoException {
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USER_ID)) {
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
    public User findByLastName(String lastName) throws DaoException {
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_LASTNAME)) {
            preparedStatement.setString(1, lastName);
            resultSet = preparedStatement.executeQuery();
            return getFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoCloser.closeResultSet(resultSet);
        }
    }

    @Override
    public User findByEmail(String email) throws DaoException {
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            return getFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoCloser.closeResultSet(resultSet);
        }
    }

    @Override
    public User findByLogin(String login) throws DaoException {
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            return getFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoCloser.closeResultSet(resultSet);
        }
    }

    @Override
    public List<User> getUserList() throws DaoException {
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_LIST)) {
            List<User> users = new ArrayList<>();
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(createEntity(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoCloser.closeResultSet(resultSet);
        }
    }

    @Override
    public List<User> findNotBannedUsers() throws DaoException {
        List<User> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(FIND_NOT_BANNED_USERS);
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                result.add(createEntity(resultSet));
            }
            } catch (SQLException e) {
                throw new DaoException(e);
            }
            return result;
        }

    @Override
    public User findUserByLoginAndPassword(String login, String password) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_LOGIN_AND_PASSWORD)) {
            log.info("logging method for finding user");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            log.info("login " + login);
            log.info("password " + password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return getFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }
@Override
    public User getFromResultSet(ResultSet resultSet) throws DaoException {
    User user = null;
        try {
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong(ID));
                user.setFirstName(resultSet.getString(FIRST_NAME));
                user.setLastName(resultSet.getString(LAST_NAME));
                user.setBirth(resultSet.getDate(BIRTH));
                user.setPhone(resultSet.getString(PHONE));
                user.setLogin(resultSet.getString(LOGIN));
                user.setPassword(resultSet.getString(PASSWORD));
                user.setEmail(resultSet.getString(EMAIL));
                user.setCity(resultSet.getString(CITY));
                user.setAddress(resultSet.getString(ADDRESS));
                user.setCountry(resultSet.getString(COUNTRY));
                user.setPostcode(resultSet.getString(POSTCODE));
                user.setRole(User.Role.valueOf(resultSet.getString(ROLE_NAME)));
                user.setBanned(resultSet.getBoolean(IS_BANNED));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return user;
    }


    @Override
    public User createEntity(ResultSet resultSet) throws DaoException {
        try {
            long id = resultSet.getLong(ID);
            String firstName = resultSet.getString(FIRST_NAME);
            String lastName = resultSet.getString(LAST_NAME);
            Date birth = resultSet.getDate(BIRTH);
            String phone = resultSet.getString(PHONE);
            String login = resultSet.getString(LOGIN);
            String password = resultSet.getString(PASSWORD);
            String email = resultSet.getString(EMAIL);
            User.Role role = User.Role.valueOf(resultSet.getString(ROLE_NAME));
            String city = resultSet.getString(CITY);
            String country = resultSet.getString(COUNTRY);
            String address = resultSet.getString(ADDRESS);
            String postcode = resultSet.getString(POSTCODE);
            boolean banned = resultSet.getBoolean(IS_BANNED);
            return (User) new User()
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setBirth(birth)
                    .setPhone(phone)
                    .setLogin(login)
                    .setPassword(password)
                    .setEmail(email)
                    .setRole(role)
                    .setCity(city)
                    .setCountry(country)
                    .setAddress(address)
                    .setPostcode(postcode)
                    .setBanned(banned)
                    .setId(id);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public boolean deleteById(User user) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return true;
    }

    @Override
    public void deleteByEmail(User user) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_EMAIL)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteByLastName(User user) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_LASTNAME)) {
            preparedStatement.setString(1, user.getLastName());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void updatePassword(User user) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD)) {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setLong(2, user.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean UserLoginIsAlreadyExist(String login) throws DaoException {
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            DaoCloser.closeResultSet(resultSet);
        }
    }
}
