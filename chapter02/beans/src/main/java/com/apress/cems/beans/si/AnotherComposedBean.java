package com.apress.cems.beans.si;

import com.apress.cems.beans.ci.SimpleBean;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface AnotherComposedBean {

    SimpleBean getSimpleBean();

    Boolean isComplex();
}
