package com.apress.cems.ex;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by iuliana.cosmina on 5/11/15.
 * Description: This class is a special exception class used for exceptions thrown the service layer
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="BadRequest")
public class InvalidCriteriaException extends RuntimeException {

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
