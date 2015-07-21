package com.epam.polygor.webstore.action;

import com.epam.polygor.webstore.listener.ContextListener;
import com.epam.polygor.webstore.model.Category;
import com.epam.polygor.webstore.model.Image;
import com.epam.polygor.webstore.model.Product;
import com.epam.polygor.webstore.service.ImageService;
import com.epam.polygor.webstore.service.ProductService;
import com.epam.polygor.webstore.servlet.Scope;
import com.epam.polygor.webstore.servlet.WebContext;
import com.epam.polygor.webstore.util.ImageResizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class CreateProductAction extends AbstractProductCreationAction {
    public static final Logger LOGGER = LoggerFactory.getLogger(CreateProductAction.class);
    private static final String EMPTY_STRING = "";
    private ResourceBundle messagesBundle;
    private String categoryName;
    private String productName;
    private String description;
    private String productManufacturer;
    private String price;
    private Image productImage;

    @Override
    public ActionResult execute(WebContext webContext) {
        messagesBundle = webContext.getMessagesBundle();
        ActionResult previousPage = new ActionResult(webContext.getPreviousURI(), true);
        getParametersFromRequest(webContext);
        List<String> validationErrors = validateInputData();
        if (validationErrors.size() > 0) {
            super.setAttributesToFlashScope(webContext); //to display on page if error happens
            webContext.setAttribute("errors", validationErrors, Scope.FLASH);
            return previousPage;
        }
        ProductService productService = new ProductService();
        if (productService.isProductExist(productName)) {
            super.setAttributesToFlashScope(webContext); //to display on page if error happens
            webContext.setAttribute
                    ("errorMessage", messagesBundle.getString("product-creation.errorAlreadyExist"), Scope.FLASH);
            return previousPage;
        }
        try {
            productService.addProduct(createProduct(webContext));
        } catch (ActionException e) {
            e.printStackTrace();
        }
        webContext.setAttribute
                ("successCreation", messagesBundle.getString("product-creation.message.success"), Scope.FLASH);
        return previousPage; //redirecting to previous page with message
    }


    /**
     * Validating input data for empty fields,
     * checking if price is number
     *
     * @return message with validation error or null if no validation errors
     */
    private List<String> validateInputData() {
        List<String> errors = new ArrayList<>();
        if (Arrays.asList(categoryName, productName,description, productManufacturer, price).contains(EMPTY_STRING)){
            errors.add(messagesBundle.getString("product-creation.error.emptyFields"));
            return errors; // return to prevent errors when trying to validate empty fields
        }
        if (Validator.isDescriptionTooLarge(description)) {
            errors.add(messagesBundle.getString("product-creation.error.descriptionTooLarge"));
        }
        if (productImage == null) {
            errors.add(messagesBundle.getString("product-creation.error.image"));
        }
        if (Validator.notNumber(price)) {
            errors.add(messagesBundle.getString("product-creation.error.price"));
        }
        if (Validator.isNumberTooLong(price)) {
            errors.add(messagesBundle.getString("product-creation.error.largeNumber"));
        }
        return errors;
    }





    private BigDecimal parseStringToBigDecimal(String s) {
        return BigDecimal.valueOf(Double.valueOf(s));
    }

    private void getParametersFromRequest(WebContext webContext){
        categoryName = webContext.getParameter(CATEGORY_NAME);
        productName = webContext.getParameter(PRODUCT_NAME);
        description = webContext.getParameter(DESCRIPTION);
        productManufacturer = webContext.getParameter(PRODUCT_MANUFACTURER);
        price = webContext.getParameter(PRICE);
        try {
            productImage = getImageFromRequest(webContext);
        } catch (IOException | ServletException e) {
            throw new ActionException("getting image from request exception error", e);
        }
    }

    private Image getImageFromRequest(WebContext webContext) throws IOException, ServletException {
        Part part = webContext.getPart(IMAGE);
        String imageName = part.getSubmittedFileName();
        String contentType = part.getContentType();
        byte[] imageBytes;
        try (InputStream content = part.getInputStream();
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            if (content.available() == 0) return null; //if nothing to read -  image bytes is empty
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = content.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            imageBytes = output.toByteArray();
        }
        byte[] reducedImage = ImageResizer.resize(imageBytes, Image.STANDARD_WIDTH, Image.STANDARD_HEIGHT);
        return new Image(imageName, contentType, reducedImage);
    }

    @SuppressWarnings("unchecked")
    private Product createProduct(WebContext webContext) throws ActionException {
        List<Category> categories = (List<Category>) webContext.getAttribute(ContextListener.CATEGORIES_LIST, Scope.APPLICATION);
        Category productCategory = null;
        //Finding category of the product from application context, category id from database needed
        for (Category category : categories) {
            if (category.getName().equals(categoryName)) {
                productCategory = category;
                break;

            }
        }
        if (productCategory == null) throw new ActionException("Such category doesn't exist " + categoryName);
        BigDecimal productPrice = new BigDecimal(String.valueOf(parseStringToBigDecimal(price)));
        return new Product(description, productCategory, productManufacturer, productName, productPrice, productImage);
    }
}

