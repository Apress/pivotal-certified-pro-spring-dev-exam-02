package com.apress.cems.repos;

/**
 * Created by iuliana.cosmina on 4/17/16.
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
