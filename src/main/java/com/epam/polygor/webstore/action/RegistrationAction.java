package com.epam.polygor.webstore.action;

import com.epam.polygor.webstore.service.UserService;
import com.epam.polygor.webstore.servlet.Scope;
import com.epam.polygor.webstore.servlet.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class RegistrationAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(RegistrationAction.class);
    private static final String EMPTY_STRING = "";
    private static final String FIRSTNAME = "firstName";
    private static final String LASTNAME = "lastName";
    private final static String LOGIN = "login";
    private final static String PASSWORD = "password";
    private final static String CONFIRM_PASSWORD = "confirmPassword";
    private final static String EMAIL = "email";
    private static final String BIRTH = "birth";
    private static final String CITY = "city";
    private static final String COUNTRY = "country";
    private static final String ADDRESS = "address";
    private static final String POSTCODE = "postcode";
    private static final String PHONE = "phone";
    private ActionResult success = new ActionResult("registration-success", true);
    private ActionResult error = new ActionResult("registration", true);
    private ResourceBundle messagesBundle;


    @Override
    public ActionResult execute(WebContext webContext) {
        messagesBundle = webContext.getMessagesBundle();
        String firstName = webContext.getParameter(FIRSTNAME);
        String lastName = webContext.getParameter(LASTNAME);
        String login = webContext.getParameter(LOGIN);
        String password = webContext.getParameter(PASSWORD);
        String confirmPassword = webContext.getParameter(CONFIRM_PASSWORD);
        String birth = webContext.getParameter(BIRTH);
        String email = webContext.getParameter(EMAIL);
        String city = webContext.getParameter(CITY);
        String country = webContext.getParameter(COUNTRY);
        String address = webContext.getParameter(ADDRESS);
        String postcode = webContext.getParameter(POSTCODE);
        String phone = webContext.getParameter(PHONE);
        UserService userService = new UserService();
        //setting attributes to flash scope for displaying them after caused by error redirect
        webContext.setAttribute(FIRSTNAME, firstName, Scope.FLASH);
        webContext.setAttribute(LASTNAME, lastName, Scope.FLASH);
        webContext.setAttribute(LOGIN, login, Scope.FLASH);
        webContext.setAttribute(BIRTH, birth, Scope.FLASH);
        webContext.setAttribute(EMAIL, email, Scope.FLASH);
        webContext.setAttribute(CITY, city, Scope.FLASH);
        webContext.setAttribute(COUNTRY, country, Scope.FLASH);
        webContext.setAttribute(ADDRESS, address, Scope.FLASH);
        webContext.setAttribute(POSTCODE, postcode, Scope.FLASH);
        webContext.setAttribute(PHONE, phone, Scope.FLASH);
        List<String> validationErrors = checkValidationErrors(login, password, confirmPassword, birth, email, city, country, address, postcode, userService);
        if (validationErrors.size() > 0) {
            webContext.setAttribute("errors", validationErrors, Scope.FLASH);
            log.debug("Registration errors: " + validationErrors);
            return error;
        }
        userService.registration(firstName, lastName, login, password, birth, email, city, address, country, postcode, phone);
        return success;
    }

    private List<String> checkValidationErrors(String login, String password, String confirmPassword, String birth, String email, String city, String country, String address, String postcode, UserService userService) {
        List<String> errors = new ArrayList<>();
        if (Arrays.asList(login, email, password, confirmPassword, city, country, address, postcode, birth).contains(EMPTY_STRING)) {
            errors.add(messagesBundle.getString("registration.error-emptyFields"));
            return errors;//return errors now to avoid validation errors
        }
        if (!Validator.isEmailValid(email)) {
            errors.add(messagesBundle.getString("registration.error.email"));
        }
        if (!Validator.isLoginValid(login)) {
            errors.add(messagesBundle.getString("registration.error.login"));
        }
        if (!password.equals(confirmPassword)) {
            errors.add(messagesBundle.getString("registration.error.password"));
        }
        if (!Validator.isPasswordValid(password)) {
            errors.add(messagesBundle.getString("registration.error.passwordRegex"));
        }

        if (!Validator.isDateValid(birth)) {
            errors.add(messagesBundle.getString("registration.error.date"));
        }
        if (!userService.isUserExist(login)) {
            errors.add(messagesBundle.getString("registration.error.userExist"));
        }
        return errors;
    }
}