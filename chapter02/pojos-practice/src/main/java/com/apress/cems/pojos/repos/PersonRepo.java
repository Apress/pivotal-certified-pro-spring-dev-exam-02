package com.apress.cems.pojos.repos;

import com.apress.cems.dao.Person;

import java.util.Set;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface PersonRepo extends AbstractRepo<Person> {
    Person findByUsername(String username);

    Set<Person> findByCompleteName(String firstName, String lastName);
}
