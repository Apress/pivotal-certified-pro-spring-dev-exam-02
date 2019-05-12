package com.apress.cems.scopes2;

import java.util.Random;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface SalaryIdea {

    Integer getAmount();

    //de-comment this and the lines in the test to witness the proxying of a default method
    /*default Integer defaultMethod() {
        System.out.println("Called from here ->" + this.hashCode() );
        return 0;
    }*/
}
