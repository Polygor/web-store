package com.epam.polygor.webstore.action;

import com.epam.polygor.webstore.model.Product;
import com.epam.polygor.webstore.service.ProductService;
import com.epam.polygor.webstore.servlet.WebContext;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

public abstract class AbstractShowProductsAction implements Action {
    ProductService productService;

    @Override
    public abstract ActionResult execute(WebContext webContext);

    protected List<Product> getProducts(String categoryName) {
        productService = new ProductService();
        return productService.getProductsForCategory(categoryName);
    }

    protected List<Product> getAllProducts(){
        productService = new ProductService();
        return  productService.getAllProducts();
    }

    protected String getCategoryNameFromPath(WebContext webContext) throws ActionException {
        String category = webContext.getFirstParameterFromPath(); //category parameter as a part of a path
        if (category != null) {
            try {
                category = URLDecoder.decode(category, "UTF-8"); //decoding category name from URL decoding
            } catch (UnsupportedEncodingException e) {
                throw new ActionException(e);
            }
        }
        return category;
    }
}
