package com.apress.cems.practice.ex;

/**
 * Created by iuliana.cosmina on 5/11/15.
 * Description: This class is a special exception class used for exceptions thrown the service layer
 */
public class InvalidCriteriaException extends Exception {

    private String fieldName;

    /**
     * Field that contains an internationalization key for exceptions thrown in service classes
     */
    private String messageKey;

    public InvalidCriteriaException(String fieldName, String messageKey) {
        this.fieldName = fieldName;
        this.messageKey = messageKey;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
