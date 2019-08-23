package com.apress.cems.util;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Created by iuliana.cosmina on 3/30/15.
 */
public class CriteriaDto implements Serializable {

    @NotEmpty
    private String fieldName;

    @NotEmpty
    private String fieldValue;

    private Boolean exactMatch;


    /**
     * Field needed just to communicate a message of no results found.
     */
    private String noResults;


    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Boolean getExactMatch() {
        return exactMatch;
    }

    public void setExactMatch(Boolean exactMatch) {
        this.exactMatch = exactMatch;
    }

    public String getNoResults() {
        return noResults;
    }

    public void setNoResults(String noResults) {
        this.noResults = noResults;
    }

    public boolean isEmpty(){
        return fieldName == null && fieldValue == null;
    }
}
