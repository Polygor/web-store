package com.epam.polygor.webstore.util;

import com.epam.polygor.webstore.dao.DaoException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoCloser {

    public static void closeResultSet(ResultSet resultSet) throws DaoException {
        try {
            if (resultSet != null) resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static void closeInputStream(InputStream inputStream) throws DaoException {
        try {
            if (inputStream != null) inputStream.close();
        } catch (IOException e) {
            throw new DaoException(e);
        }
    }

    public static void closeOutputStream(OutputStream outputStream) throws DaoException {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException e) {
            throw new DaoException(e);
        }
    }

    public static void closeAll(InputStream is, OutputStream os) throws DaoException {
        closeInputStream(is);
        closeOutputStream(os);
    }
}