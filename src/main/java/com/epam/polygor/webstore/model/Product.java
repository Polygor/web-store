package com.epam.polygor.webstore.model;


import java.math.BigDecimal;

public class Product extends BaseEntity {
    private String name;
    private Category category;
    private String description;
    private String manufacturer;
    private Image image;
    private BigDecimal price;
    private boolean inStock;

    public Product() {
    }

    public Product(long id) {
        super(id);
    }

    public Product(String description, Category category, String manufacturer, String name, BigDecimal price, Image image) {
        this.description = description;
        this.category = category;
        this.manufacturer = manufacturer;
        this.name = name;
        this.price = price;
        this.image = image;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public Product setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Product setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public Product setCategory(Category category) {
        this.category = category;
        return this;
    }

    public Image getImage() {
        return image;
    }

    public Product setImage(Image image) {
        this.image = image;
        return this;
    }

    public boolean isInStock() {
        return inStock;
    }

    public Product setInStock(boolean inStock) {
        this.inStock = inStock; return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        if (!super.equals(o)) return false;

        Product product = (Product) o;

        if (inStock != product.inStock) return false;
        if (category != null ? !category.equals(product.category) : product.category != null) return false;
        if (description != null ? !description.equals(product.description) : product.description != null) return false;
        if (image != null ? !image.equals(product.image) : product.image != null) return false;
        if (manufacturer != null ? !manufacturer.equals(product.manufacturer) : product.manufacturer != null)
            return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (price != null ? !price.equals(product.price) : product.price != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (manufacturer != null ? manufacturer.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (inStock ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "category=" + category +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", image=" + image +
                ", price=" + price +
                ", inStock=" + inStock +
                '}';
    }
}




