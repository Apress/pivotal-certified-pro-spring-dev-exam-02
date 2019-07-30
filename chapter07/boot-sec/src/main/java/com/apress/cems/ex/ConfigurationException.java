package com.apress.cems.ex;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public class ConfigurationException extends RuntimeException {
    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
