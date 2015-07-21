package com.epam.polygor.webstore.dao;

import com.epam.polygor.webstore.model.User;
import java.util.List;

public interface UserDao extends Dao<User> {
     User findByLastName(String lastName) throws DaoException;

     User findByEmail(String email) throws DaoException;

    User findByLogin(String login) throws DaoException;

     User findUserByLoginAndPassword(String login, String password) throws DaoException;

    void deleteByEmail(User user) throws DaoException;

     void deleteByLastName(User user) throws DaoException;

    void updatePassword(User user) throws DaoException;

     List<User> getUserList() throws DaoException;

    List<User> findNotBannedUsers() throws DaoException;

    boolean UserLoginIsAlreadyExist(String login) throws DaoException;
}