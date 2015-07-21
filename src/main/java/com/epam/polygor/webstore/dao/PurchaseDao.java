package com.epam.polygor.webstore.dao;

import com.epam.polygor.webstore.model.Purchase;

import java.util.List;

public interface PurchaseDao extends Dao<Purchase> {

    List<Purchase> getPurchaseList() throws DaoException;

}
