package com.epam.polygor.webstore.dao.implementations;

import com.epam.polygor.webstore.dao.AbstractJDBCDao;
import com.epam.polygor.webstore.dao.DaoException;
import com.epam.polygor.webstore.dao.StatusDao;
import com.epam.polygor.webstore.model.Status;
import com.epam.polygor.webstore.util.DaoCloser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class SQLStatusDao extends AbstractJDBCDao<Status> implements StatusDao {
    private static final String FIND_BY_STATUS = "SELECT NAME FROM STATUS WHERE name = ?";
    private static final String INSERT_STATUS= "INSERT INTO status VALUES (DEFAULT, ?)";
    private static final String UPDATE = "UPDATE status SET name = ? WHERE STATUS_ID = ?";
    private static final String DELETE_BY_ID = "DELETE FROM status WHERE STATUS_ID = ?";
    private static final String FIND_BY_ID = "SELECT status_id, NAME FROM status WHERE STATUS_ID = ?";
    public SQLStatusDao(Connection connection) {
        super(connection);
    }

    @Override
    public Status createEntity(ResultSet resultSet) throws DaoException {
        try {
            long id = resultSet.getLong(STATUS_ID);
            String name = resultSet.getString(NAME);
            return (Status) new Status()
                    .setName(name)
                    .setId(id);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Status getFromResultSet(ResultSet resultSet) throws DaoException {
        Status status = null;
        try {
            if (resultSet.next()) {
                status = new Status();
                status.setId(resultSet.getLong(STATUS_ID));
                status.setName(resultSet.getString(NAME));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return status;
    }

    @Override
        public Status findByStatus(String status) throws DaoException {
            try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_STATUS)) {
                preparedStatement.setString(1, status);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return getFromResultSet(resultSet);
                }
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }


    @Override
    public Status insert(Status status) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STATUS)) {
            preparedStatement.setString(1, status.getName());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return status;
    }


    @Override
    public Status findById(long id) throws DaoException {
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
    public boolean update(Status status) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, status.getName());
            preparedStatement.setLong(2, status.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return true;
    }

    @Override
    public boolean deleteById(Status status) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, status.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return true;
    }
}
