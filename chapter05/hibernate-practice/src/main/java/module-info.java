/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
module com.apress.cems.hib.practice {
    requires com.apress.cems.dao;
    requires com.apress.cems.repos;
    requires com.apress.cems.aop;

    requires org.hibernate.orm.core;

    requires org.apache.commons.lang3;
    requires java.sql;
    requires org.slf4j;
    requires ojdbc7;
    requires java.naming;
    requires jsr250.api;

    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.jdbc;
    requires spring.tx;
    requires spring.orm;
}