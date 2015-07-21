package com.epam.polygor.webstore.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Order implements Comparable<Order> {
    private List<Purchase> purchases;
    private Timestamp date;

    public Order(List<Purchase> purchases, Timestamp date) {
        this.date = date;
        this.purchases = purchases;
    }
    public Order() {
    }

    public Timestamp getDate() {
        return date;
    }

    public Order setDate(Timestamp date) {
        this.date = date;
        return this;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public Order setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
        return this;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        for (Purchase purchase : purchases) {
            totalPrice = totalPrice.add(purchase.getPrice());
        }
        return totalPrice;
    }

    public int getItemsAmount() {
        return purchases.size();
    }

    @Override
    public int compareTo(Order o) {
            return this.date.compareTo(o.getDate());
        }
    }