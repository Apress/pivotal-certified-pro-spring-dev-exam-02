/**
 * Created by iuliana.cosmina on 21/01/19.
 */
module com.apress.cems.aop {
    exports com.apress.cems.aop.service;
    exports com.apress.cems.aop.exception;
    requires com.apress.cems.dao;
    requires com.apress.cems.repos;
    requires org.apache.commons.lang3;
    requires java.sql;
    requires spring.jdbc;

    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires org.aspectj.weaver;
    requires org.slf4j;
}