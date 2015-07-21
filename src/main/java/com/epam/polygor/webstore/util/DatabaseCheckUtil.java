package com.epam.polygor.webstore.util;

import com.epam.polygor.webstore.dao.DaoException;
import com.epam.polygor.webstore.dao.DaoFactory;
import com.epam.polygor.webstore.dao.DaoManager;
import com.epam.polygor.webstore.dao.implementations.SQLDaoFactory;
import com.ibatis.common.jdbc.ScriptRunner;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class DatabaseCheckUtil {
    public static final String RESOURCE_PATH = "sql/database.sql";
    private static final String CHECK_DATABASE = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC';";
    public static final String COUNT = "COUNT(*)";
    private DaoFactory daoFactory = SQLDaoFactory.getInstance();
    private DaoManager daoManager = daoFactory.getDaoManager();
    private Connection connection = daoManager.getConnection();

    public void create() {
        InputStream resourceAsStream = getClass().getResourceAsStream(RESOURCE_PATH);
        ScriptRunner sr = new ScriptRunner(connection, false, false);
        Reader reader = new BufferedReader(new InputStreamReader(resourceAsStream));
        try {
            sr.runScript(reader);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean check() {
        boolean result = false;
        String count = COUNT;
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_DATABASE)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result = resultSet.getBoolean(count);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }
}
