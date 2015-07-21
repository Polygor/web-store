package com.epam.polygor.webstore.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Purchase extends BaseEntity {
    private Product product;
    private BigDecimal price;
    private Status status;
    private Timestamp date;
    private User user;

    public Purchase() {
    }

    public Purchase(Product product, BigDecimal price, Timestamp date, User user) {
        this.product = product;
        this.price = price;
        this.date = date;
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public Purchase setProduct(Product product) {
        this.product = product;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Purchase setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public Purchase setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Timestamp getDate() {
        return date;
    }

    public Purchase setDate(Timestamp date) {
        this.date = date;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Purchase setUser(User user) {
        this.user = user;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Purchase purchase = (Purchase) o;

        if (date != null ? !date.equals(purchase.date) : purchase.date != null) return false;
        if (price != null ? !price.equals(purchase.price) : purchase.price != null) return false;
        if (product != null ? !product.equals(purchase.product) : purchase.product != null) return false;
        if (status != null ? !status.equals(purchase.status) : purchase.status != null) return false;
        if (user != null ? !user.equals(purchase.user) : purchase.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "product=" + product +
                ", price=" + price +
                ", status=" + status +
                ", date=" + date +
                ", user=" + user +
                '}';
    }
}