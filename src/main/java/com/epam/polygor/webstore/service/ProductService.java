package com.epam.polygor.webstore.service;

import com.epam.polygor.webstore.dao.*;
import com.epam.polygor.webstore.model.Category;
import com.epam.polygor.webstore.model.Image;
import com.epam.polygor.webstore.model.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductService extends AbstractService {
    public ProductService() {
    }

    public Product getProductByID(long id) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            ProductDao productDao = daoManager.getProductDao();
            return productDao.findById(id);
        }
    }

    public Product getProductByName(String productName) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            ProductDao productDao = daoManager.getProductDao();
            return productDao.findByName(productName);
        }
    }

    public boolean isProductExist(String productName) {
        return getProductByName(productName) != null;
    }

    public void addProduct(Product product) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            ProductDao productDao = daoManager.getProductDao();
            ImageService imageService = new ImageService();
            imageService.addImage(product.getImage());
            Image image = imageService.getImageByName(product.getImage().getName());
            product.setImage(image);
            daoManager.beginTransaction();
            productDao.insert(product);
            daoManager.closeTransaction();
        }
    }

    public void deleteProduct(Long id) {
        Product product = getProductByID(id);
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            ProductDao productDao = daoManager.getProductDao();
            ImageDao imageDao = daoManager.getImageDao();
            daoManager.beginTransaction();
            productDao.deleteById(product);
            imageDao.deleteById(product.getImage());
            daoManager.closeTransaction();
        }
    }

    public List<Product> getProductsForCategory(String categoryName) {
        List<Product> products = new ArrayList<>();
        List<Product> productList;
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            ProductDao productDao = daoManager.getProductDao();
            productList = productDao.getProductList();
            for (Product product : productList) {
                if (categoryName.equals(product.getCategory().getName())) {
                    products.add(product);
                }
            }
            return products;
        }
    }

    public List<Product> getAllProducts() {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            ProductDao productDao = daoManager.getProductDao();
            return productDao.getProductList();
        }
    }
}











