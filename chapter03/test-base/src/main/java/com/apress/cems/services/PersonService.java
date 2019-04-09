package com.apress.cems.services;

import com.apress.cems.dao.Person;

import java.util.Optional;
import java.util.Set;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface PersonService extends AbstractService<Person> {

    Person createPerson(String firstName, String lastName);

    Optional<Person> findByUsername(String username);

    Optional<Person> findByFirstNameAndLastName(String firstName, String lastName);
}
