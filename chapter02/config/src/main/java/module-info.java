/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
module com.apress.cems.config {
    requires com.apress.cems.dao;
    requires org.apache.commons.lang3;
    requires java.sql;
    requires org.slf4j;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.jdbc;
    requires java.naming;
    requires java.annotation;

    requires com.apress.cems.pojos;
    requires ojdbc8;
}