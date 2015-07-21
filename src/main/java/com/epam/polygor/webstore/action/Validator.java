package com.epam.polygor.webstore.action;

public class Validator {
    private static final String LOGIN_REGEX = "^[A-Za-z]([\\.A-Za-z0-9-]{1,18})([A-Za-z0-9])$";
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-zA-Z])(?!.*\\s).{8,20}$";
    private static final String EMAIL_REGEX = "[A-Za-z0-9_]{3,24}@[A-Za-z]{2,16}.[A-Za-z]{2,6}";
    private static final String DATE_REGEX = "^(19|20)\\d\\d[\\-](0[1-9]|1[012])[\\-](0[1-9]|[12][0-9]|3[01])$";
    private static final String INTEGER_NUMBER_REGEX = "[0-9]+";
    private static final String DECIMAL_NUMBER_REGEX = "[0-9]+[.][0-9]+";
    private static final String MAX_NUMBER_SIZE_REGEX = ".{9}";
    private static final int MAX_DESCRIPTION_SIZE = 1024;

    public static boolean isEmailValid(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public static boolean isLoginValid(String login) {
        return (login.matches(LOGIN_REGEX));
    }

    public static boolean isIntegerNumber(String s) {
        return s.matches(INTEGER_NUMBER_REGEX);
    }

    public static boolean isDecimalNumber(String str) {
        return str.matches(DECIMAL_NUMBER_REGEX);
    }

    public static boolean notNumber(String str) {
        return !Validator.isIntegerNumber(str) && !Validator.isDecimalNumber(str);
    }

    public static boolean isNumberTooLong(String number) {
        return number.matches(MAX_NUMBER_SIZE_REGEX);
    }

    public static boolean isDateValid(String date) {
        return (date.matches(DATE_REGEX));
    }

    public static boolean isPasswordValid(String password) {
        return (password.matches(PASSWORD_REGEX));
    }

    public static boolean isDescriptionTooLarge(String description) {
        return description.length() > MAX_DESCRIPTION_SIZE;
    }
}
