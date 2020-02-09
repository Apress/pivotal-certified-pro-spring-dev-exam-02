/**
 * @author Iuliana Cosmina
 * @since 1.0
 */

module com.apress.cems.classic.rest {
    requires java.naming;

    requires spring.core;
    requires spring.webmvc;
    requires spring.context;
    requires spring.web;

    requires org.slf4j;
    requires java.annotation;
    requires com.zaxxer.hikari;
    requires spring.beans;
    requires java.sql;

    requires java.validation;
    requires spring.data.jpa;
    requires spring.jdbc;
    requires spring.tx;
    requires spring.orm;
    requires java.persistence;
    requires spring.data.commons;
    requires com.fasterxml.jackson.databind;
    requires javax.servlet.api;

    opens com.apress.cems.config to spring.core;
}