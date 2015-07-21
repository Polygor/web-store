package com.epam.polygor.webstore.service;


import com.epam.polygor.webstore.dao.DaoFactory;
import com.epam.polygor.webstore.dao.implementations.SQLDaoFactory;

abstract class AbstractService {
    protected DaoFactory daoFactory = SQLDaoFactory.getInstance();
}