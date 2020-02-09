/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
module com.apress.cems.sec.dj {
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
    requires spring.security.core;

    exports com.apress.cems.dj;
    exports com.apress.cems.dj.repos;
    exports com.apress.cems.dj.problem;
    exports com.apress.cems.dj.services;
    exports com.apress.cems.dj.services.wrappers;
}