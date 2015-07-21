package com.epam.polygor.webstore.service;

import com.epam.polygor.webstore.dao.*;
import com.epam.polygor.webstore.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public class PurchaseService extends AbstractService {
    private static final Logger log = LoggerFactory.getLogger(PurchaseService.class);
    public PurchaseService() {
    }

    /**
     * Further method gets user's order list and forms with it's order list.
     *  Order is list of purchases committed at one time
     */

    public List<Order> getUserOrderList (long userID) {
        List<Purchase> userPurchases = new ArrayList<>();
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            PurchaseDao purchaseDao = daoManager.getPurchaseDao();
            List<Purchase> purchases = purchaseDao.getPurchaseList();
            for (Purchase purchase : purchases) {
                if (userID == purchase.getUser().getId()) {
                    userPurchases.add(purchase);
                }
            }
            return getOrderList(userPurchases);
        }
    }

    public void updatePurchases (Map<Long, String> purchaseStatusById) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            PurchaseDao purchaseDao = daoManager.getPurchaseDao();
            StatusDao statusDao = daoManager.getStatusDao();
            daoManager.beginTransaction();
            for (Map.Entry<Long,String> longStringEntry : purchaseStatusById.entrySet()) {
                Long purchaseId = longStringEntry.getKey();
                String purchaseNewStatus = longStringEntry.getValue();
                Purchase purchase = purchaseDao.findById(purchaseId);
                // to compare status condition
                if (purchase.getStatus().getName().equalsIgnoreCase(purchaseNewStatus)) {
                    continue; // no update needed

                }
                //check status name equals represented in database
                switch (purchaseNewStatus) {
                    case Status.CANCELED:
                    case Status.DELIVERY:
                    case Status.DELIVERED:
                    case Status.UNPAID:
                    case Status.PAID:
                        Status status = statusDao.findByStatus(purchaseNewStatus);
                        purchase.setStatus(status);
                        purchaseDao.update(purchase);
                }
            }
            daoManager.closeTransaction();
        }
    }


    public void addPurchase(Purchase purchase) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            PurchaseDao purchaseDao = daoManager.getPurchaseDao();
            StatusDao statusDao = daoManager.getStatusDao();
            purchase.setStatus(statusDao.findById(3));
            daoManager.beginTransaction();
            purchaseDao.insert(purchase);
            daoManager.closeTransaction();
        }
    }

    public Purchase getPurchase(long purchaseId) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            PurchaseDao purchaseDao = daoManager.getPurchaseDao();
            return purchaseDao.findById(purchaseId);
        }
    }

    private Set<Timestamp> getUniqueDatesFromPurchaseList(List<Purchase> purchaseList) {
        Set<Timestamp> dates = new LinkedHashSet<>();
        for (Purchase purchase : purchaseList) {
            dates.add(purchase.getDate());
        }
        return dates;
    }

private List<Order> getOrderList(List<Purchase> purchases) {
    List<Order> orders = new ArrayList<>();
    Set<Timestamp> dates = getUniqueDatesFromPurchaseList(purchases);
    for (Timestamp date : dates) {
        List<Purchase> purchaseListWithTheSameDate = purchases.stream()
                .filter(purchase -> purchase.getDate().equals(date)) //get purchases with same date
                .collect(Collectors.toList());
        sortPurchaseByPrice(purchaseListWithTheSameDate);
        orders.add(new Order(purchaseListWithTheSameDate, date)); //order creation with previous data
    }
    orders.sort(Order::compareTo);
    return orders;
}

    private void sortPurchaseByPrice(List<Purchase> purchaseList) {
        purchaseList.sort((o1, o2) -> o1.getPrice().compareTo(o2.getPrice()));
    }
}