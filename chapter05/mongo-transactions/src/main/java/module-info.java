/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
module com.apress.cems.mongo.tx {
    requires org.slf4j;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.tx;
    requires java.naming;
    requires jsr250.api;
    requires spring.data.commons;
    requires spring.data.mongodb;
}