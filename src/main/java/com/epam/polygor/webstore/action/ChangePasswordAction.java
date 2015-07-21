package com.epam.polygor.webstore.action;

import com.epam.polygor.webstore.model.User;
import com.epam.polygor.webstore.service.UserService;
import com.epam.polygor.webstore.servlet.Scope;
import com.epam.polygor.webstore.servlet.WebContext;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChangePasswordAction implements Action {
    private static final String OLD_PASSWORD = "oldPassword";
    private static final String NEW_PASSWORD = "newPassword";
    private static final String CONFIRM_NEW_PASSWORD = "confirmNewPassword";
    private ResourceBundle messagesBundle;
    private ActionResult error = new ActionResult("user/change-password", false);
    private ActionResult success = new ActionResult("user", true);

    @Override
    public ActionResult execute(WebContext webContext) {
        UserService userService = new UserService();
        messagesBundle = webContext.getMessagesBundle();
        String oldPassword = webContext.getParameter(OLD_PASSWORD);
        String newPassword = webContext.getParameter(NEW_PASSWORD);
        String confirmNewPassword = webContext.getParameter(CONFIRM_NEW_PASSWORD);

        User user = (User) webContext.getAttribute("user", Scope.SESSION);
        List<String> validationErrors = checkValidationErrors(oldPassword, user, newPassword, confirmNewPassword);
        if (validationErrors.size() > 0) {
            webContext.setAttribute("errors", validationErrors, Scope.FLASH);
            return error;
        }
        userService.changeUserPassword(user, newPassword);
        webContext.setAttribute("successMessage", "Password changed successfully", Scope.FLASH);
        return success;
    }


    private List<String> checkValidationErrors(String oldPassword, User user, String newPassword, String confirmPassword) {
        List<String> errors = new ArrayList<>();
        String currentUserPassword = user.getPassword();
        if (!oldPassword.equals(currentUserPassword)) {
            errors.add(messagesBundle.getString("change-password.error.wrongPassword"));
        }
        if (!newPassword.equals(confirmPassword)) {
            errors.add(messagesBundle.getString("change-password.error.passwordsNotEqual"));
            return errors;
        }
        if (oldPassword.equals(newPassword)) {
            errors.add(messagesBundle.getString("change-password.error.theSamePassword"));
        }
        return errors;
    }
}
