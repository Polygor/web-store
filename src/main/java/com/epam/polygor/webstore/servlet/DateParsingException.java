package com.epam.polygor.webstore.servlet;

public class DateParsingException extends RuntimeException {

    public DateParsingException(String message) {
        super(message);
    }

    public DateParsingException(Throwable cause) {
        super(cause);
    }
}
