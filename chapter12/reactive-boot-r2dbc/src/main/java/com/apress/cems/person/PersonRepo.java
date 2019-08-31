package com.apress.cems.person;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface PersonRepo extends ReactiveCrudRepository<Person, Long> {
}
