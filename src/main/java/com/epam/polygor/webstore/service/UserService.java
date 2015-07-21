package com.epam.polygor.webstore.service;


import com.epam.polygor.webstore.dao.*;
import com.epam.polygor.webstore.model.User;

import java.sql.Date;
import java.util.List;

public class UserService extends AbstractService {
    public UserService() {
    }
    public User registration(String firstName, String lastName, String login, String password, String birth, String email, String city, String address, String country, String postcode, String phone) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            UserDao userDao = daoManager.getUserDao();
            User user = new User();
            user.setRole(User.Role.USER);
            user.setLogin(login);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(password);
            user.setEmail(email);
            user.setBirth(Date.valueOf(birth));
            user.setCity(city);
            user.setCountry(country);
            user.setPostcode(postcode);
            user.setAddress(address);
            user.setPhone(phone);
            return userDao.insert(user);
        }
    }

    public boolean userAlreadyExist(String login) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            UserDao userDao = daoManager.getUserDao();
            return userDao.UserLoginIsAlreadyExist(login);
        }
    }

    public boolean isUserExist(String login) {
        return !userAlreadyExist(login);
    }

    public User loginUser(String login, String password) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            UserDao userDao = daoManager.getUserDao();
            User user = userDao.findUserByLoginAndPassword(login, password);
            if (user == null) {
                return null;
            } else return user;
        }
    }



    public boolean setUserBan(long id, boolean ban) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            UserDao userDao = daoManager.getUserDao();
            User findUser = userDao.findById(id);
            if (findUser == null) return false;
            findUser.setBanned(ban);
            userDao.deleteById(findUser);
            return true;
        }
    }

    public void changeUserPassword(User user, String newPassword) {
        user.setPassword(newPassword);
         try  (DaoManager daoManager = daoFactory.getDaoManager()){
            UserDao userDao = daoManager.getUserDao();
            userDao.updatePassword(user);
        }
    }

    public List<User> getAllUsers() {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            UserDao userDao = daoManager.getUserDao();
            return userDao.getUserList();
        }

    }

    public List<User> getNotBannedUsers() {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            UserDao userDao = daoManager.getUserDao();
            return userDao.findNotBannedUsers();
        }
    }

    public User findUser (long userId) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            UserDao userDao = daoManager.getUserDao();
            return userDao.findById(userId);
        }
    }

    public User findUserByEmail(String email) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            UserDao userDao = daoManager.getUserDao();
            return userDao.findByEmail(email);
        }
    }

    public boolean setUserRole(long userId, User.Role role) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            UserDao userDao = daoManager.getUserDao();
            User user = userDao.findById(userId);
            user.setRole(role);
            userDao.update(user);
            return true;
        }
    }
}