/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
module com.apress.cems.dj.practice {
    requires com.apress.cems.dao;
    requires org.hibernate.orm.core;

    requires org.apache.commons.lang3;
    requires java.sql;
    requires org.slf4j;
    requires java.naming;
    requires java.annotation;

    requires spring.data.jpa;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.jdbc;
    requires spring.tx;
    requires spring.orm;
    requires java.persistence;
    requires spring.data.commons;
}