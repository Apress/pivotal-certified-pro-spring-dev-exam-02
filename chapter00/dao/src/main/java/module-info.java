/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
module com.apress.cems.dao {
    requires java.persistence;
    requires spring.context;

    exports com.apress.cems.dao;
    exports com.apress.cems.util;
    exports com.apress.cems.ex;
}