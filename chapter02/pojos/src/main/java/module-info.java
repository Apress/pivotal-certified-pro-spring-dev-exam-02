/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
module com.apress.cems.pojos {
    requires com.apress.cems.dao;
    requires org.apache.commons.lang3;
    requires java.sql;

    exports com.apress.cems.pojos.repos;
    exports com.apress.cems.pojos.services;
    exports com.apress.cems.pojos.services.impl;
}