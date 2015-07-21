package com.epam.polygor.webstore.dao;

import com.epam.polygor.webstore.model.Status;

import java.util.List;

public interface StatusDao extends Dao<Status> {

    Status findByStatus(String status) throws DaoException;
}
