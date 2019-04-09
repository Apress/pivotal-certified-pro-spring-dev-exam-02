package com.apress.cems.repos;

import com.apress.cems.dao.Person;

import java.util.Optional;
import java.util.Set;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface PersonRepo extends AbstractRepo<Person> {
    Optional<Person> findByUsername(String username);

    Optional<Person> findByCompleteName(String firstName, String lastName);

    Set<Person> findAll();
}
