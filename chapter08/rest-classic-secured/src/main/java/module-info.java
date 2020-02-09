/**
 * @author Iuliana Cosmina
 * @since 1.0
 */

module com.apress.cems.classic.rest.sec {
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
    requires spring.security.web;
    requires spring.security.core;
    requires spring.security.config;

    exports com.apress.cems.rest.sec.config;
    exports com.apress.cems.rest.sec.controllers;
    exports com.apress.cems.rest.sec.problem;
    opens com.apress.cems.rest.sec.config to spring.core;
}