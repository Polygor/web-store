package com.epam.polygor.webstore.model;

public class Status extends BaseEntity {
    public static final String PAID = "Paid";
    public static final String UNPAID = "Unpaid";
    public static final String DELIVERY = "Delivery";
    public static final String DELIVERED = "Delivered";
    public static final String CANCELED = "Canceled";
    private String name;

    public Status() {
    }

    public Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Status setName(String name) {
        this.name = name; return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Status)) return false;
        if (!super.equals(o)) return false;

        Status status = (Status) o;

        if (name != null ? !name.equals(status.name) : status.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Status{" +
                "name='" + name + '\'' +
                '}';
    }
}

