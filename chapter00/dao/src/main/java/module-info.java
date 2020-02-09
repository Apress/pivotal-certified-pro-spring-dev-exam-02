/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
module com.apress.cems.dao {
    requires java.persistence;
    requires spring.context;
    requires java.validation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;

    exports com.apress.cems.dao;
    exports com.apress.cems.dto;
    exports com.apress.cems.util;
    exports com.apress.cems.ex;
}