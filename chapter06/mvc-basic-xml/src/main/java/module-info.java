/**
 * @author Iuliana Cosmina
 * @since 1.0
 */

module com.apress.cems.web.xml {
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

    exports com.apress.cems.web.config;
    exports com.apress.cems.web.controllers;
    exports com.apress.cems.web.problem;
    opens com.apress.cems.web.config to spring.core;
}