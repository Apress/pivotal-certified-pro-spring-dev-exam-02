package com.apress.cems.reactive.person;

import com.apress.cems.person.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface PersonReactiveService {

    Mono<Person> findById(Long id);

    Flux<Person> findAll();

    Mono<Person> save(Mono<Person> personMono);

    Mono<Void> update(Long id, Mono<Person> personMono);

    Mono<Void> delete(Long id);
}
