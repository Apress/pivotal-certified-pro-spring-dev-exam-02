/**
 * @author Iuliana Cosmina
 * @since 1.0
 */

module com.apress.cems.sec.practice {
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
    requires java.validation;
    requires spring.security.web;
    requires spring.security.config;
    requires spring.security.core;
    requires thymeleaf.extras.springsecurity5;

    exports com.apress.cems.sec.config;
    exports com.apress.cems.sec.controllers;
    exports com.apress.cems.sec.problem;
    opens com.apress.cems.sec.config to spring.core;
}