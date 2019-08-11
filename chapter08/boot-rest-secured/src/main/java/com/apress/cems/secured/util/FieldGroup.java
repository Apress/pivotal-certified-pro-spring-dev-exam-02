package com.apress.cems.secured.util;

/**
 * Created by iuliana.cosmina on 3/31/15.
 */
public enum FieldGroup {
    FIRSTNAME,
    LASTNAME,
    USERNAME,
    HIREDIN;

    public static FieldGroup getField(String field){
        return FieldGroup.valueOf(field.toUpperCase());
    }
}
