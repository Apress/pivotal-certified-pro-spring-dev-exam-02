/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
module com.apress.cems.repos {
    requires com.apress.cems.dao;
    requires spring.context;
    requires spring.beans;
    requires spring.jdbc;
    requires java.sql;
    requires org.apache.commons.lang3;

    exports com.apress.cems.repos;
}