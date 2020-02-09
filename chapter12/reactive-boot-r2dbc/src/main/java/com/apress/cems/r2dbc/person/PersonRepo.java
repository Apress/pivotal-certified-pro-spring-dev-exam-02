package com.apress.cems.r2dbc.person;

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

    @Query("select * from Person p where p.firstname=:fn")
    Flux<Person> findByFirstname(@Param("fn") String firstname);

    @Query("select * from Person p where p.loginuser=:loginuser")
    Mono<Person> findByLoginuser(String loginuser);
}


