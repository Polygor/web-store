package com.epam.polygor.webstore.dao.implementations;

import com.epam.polygor.webstore.dao.DaoException;
import com.epam.polygor.webstore.dao.ImageDao;
import com.epam.polygor.webstore.dao.AbstractJDBCDao;
import com.epam.polygor.webstore.model.Image;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLImageDao extends AbstractJDBCDao<Image> implements ImageDao {
    private static final String INSERT_IMAGE = "INSERT INTO IMAGE (IMAGE_ID, IMAGENAME, CONTENT, CONTENT_TYPE, LAST_MODIFIED) VALUES (default, ?, ?, ?, ?)";
    private static final String GET_IMAGES_LIST = "SELECT IMAGE_ID, IMAGENAME, CONTENT_TYPE, CONTENT, LAST_MODIFIED FROM IMAGE";
    private static final String FIND_IMAGE_BY_NAME = "SELECT * FROM IMAGE WHERE imagename=?";
    private static final String FIND_BY_ID = "SELECT * FROM IMAGE WHERE IMAGE_ID = ?";
    private static final String UPDATE = "UPDATE IMAGE SET imagename = ?, content=?, CONTENT_TYPE=?, last_modified = ? WHERE IMAGE_ID = ?";
    private static final String DELETE_IMAGE_BY_ID = "DELETE FROM IMAGE WHERE IMAGE_ID = ?";
    private static final String CONTENT = "content";
    private static final String IMAGENAME = "imagename";
    private static final String LAST_MODIFIED = "LAST_MODIFIED";
    private static final String CONTENT_TYPE = "CONTENT_TYPE";
    public SQLImageDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Image> getImageList() throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_IMAGES_LIST)) {
            List<Image> images = new ArrayList<>();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    images.add(createEntity(resultSet));
                }
            }
            return images;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public Image findByImagename(String name) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_IMAGE_BY_NAME)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return getFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return null;
    }



    @Override
    public Image insert(Image image) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_IMAGE)) {
            InputStream inputStream = new ByteArrayInputStream(image.getContent());
            statement.setString(1, image.getName());
            statement.setBlob(2, inputStream);
            statement.setString(3, image.getContentType());
            statement.setTimestamp(4, image.getLastModified());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return image;
    }


    @Override
    public Image findById(long id) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return getFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return null;
    }


    @Override
    public boolean update(Image image) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            InputStream inputStream = new ByteArrayInputStream(image.getContent());
            statement.setString(1, image.getName());
            statement.setBlob(2, inputStream);
            statement.setString(3, image.getContentType());
            statement.setTimestamp(4, image.getLastModified());
            statement.setLong(5, image.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return true;
    }

    @Override
    public boolean deleteById(Image image) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_IMAGE_BY_ID)) {
            preparedStatement.setLong(1, image.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return true;
    }

    @Override
    public Image createEntity(ResultSet resultSet) throws DaoException {
        try {
            long id = resultSet.getLong(IMAGE_ID);
            String name = resultSet.getString(IMAGENAME);
            byte[]content = resultSet.getBytes(CONTENT);
            String contentType = resultSet.getString(CONTENT_TYPE);
            Timestamp timestamp = resultSet.getTimestamp(LAST_MODIFIED);
            return (Image) new Image()
                    .setName(name)
                    .setContent(content)
                    .setContentType(contentType)
                    .setLastModified(timestamp)
                    .setId(id);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Image getFromResultSet(ResultSet resultSet) throws DaoException {
        Image image;
        try {
            image = new Image();
            image.setId(resultSet.getLong(IMAGE_ID));
                image.setName(resultSet.getString(IMAGENAME));
                image.setContent(resultSet.getBytes(CONTENT));
                image.setLastModified(resultSet.getTimestamp(LAST_MODIFIED));
                image.setContentType(resultSet.getString(CONTENT_TYPE));
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        return image;
    }
}
