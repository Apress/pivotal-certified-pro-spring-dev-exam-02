package com.apress.cems.person;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface PersonRepo extends ReactiveCrudRepository<Person, Long> {

    @Query("select * from Person p where p.firstName=:fn")
    Flux<Person> findByFirstName(@Param("fn") String firstName);

    @Query("select * from Person p where p.username like '%' || ?1 || '%'")
    Mono<Person> findByUsername(String username);
}


