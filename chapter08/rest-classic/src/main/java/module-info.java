/**
 * @author Iuliana Cosmina
 * @since 1.0
 */

module com.apress.cems.classic.rest {
    requires com.apress.cems.dao;
    requires com.apress.cems.dj;

    requires java.naming;

    requires spring.core;
    requires spring.webmvc;
    requires spring.context;
    requires spring.web;

    requires javax.servlet.api;
    requires org.slf4j;
    requires java.annotation;
    requires com.zaxxer.hikari;
    requires spring.beans;
    requires java.sql;

    requires thymeleaf.spring5;
    requires thymeleaf;
    requires com.fasterxml.jackson.databind;

    exports com.apress.cems.rest.config;
    exports com.apress.cems.rest.controllers;
    exports com.apress.cems.rest.problem;
    opens com.apress.cems.rest.config to spring.core;
}