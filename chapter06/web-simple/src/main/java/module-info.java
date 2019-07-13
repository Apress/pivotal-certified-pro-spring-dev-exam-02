/**
 * @author Iuliana Cosmina
 * @since 1.0
 */

module com.apress.cems.web {
    requires java.naming;

    requires spring.core;
    requires spring.webmvc;
    requires spring.context;
    requires spring.web;

    requires org.eclipse.jetty.servlet;
    requires org.eclipse.jetty.server;
    requires org.eclipse.jetty.webapp;
    requires org.eclipse.jetty.annotations;

    requires javax.servlet.api;
    requires org.slf4j;
    requires java.annotation;
    requires org.eclipse.jetty.util;

    exports com.apress.cems.web;
    exports com.apress.cems.web.config;
    exports com.apress.cems.web.controllers;
    opens com.apress.cems.web.config to spring.core;
}