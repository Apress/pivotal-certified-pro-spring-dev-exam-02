/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
module com.apress.cems.tx {
    requires com.apress.cems.dao;
    requires com.apress.cems.repos;
    requires com.apress.cems.aop;
    requires org.apache.commons.lang3;
    requires java.sql;
    requires org.slf4j;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.jdbc;
    requires spring.tx;
    requires java.naming;
    requires java.annotation;
}