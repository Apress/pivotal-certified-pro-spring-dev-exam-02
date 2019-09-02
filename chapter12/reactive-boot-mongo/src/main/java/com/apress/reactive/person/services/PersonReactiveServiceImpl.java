/*
Freeware License, some rights reserved

Copyright (c) 2019 Iuliana Cosmina

Permission is hereby granted, free of charge, to anyone obtaining a copy 
of this software and associated documentation files (the "Software"), 
to work with the Software within the limits of freeware distribution and fair use. 
This includes the rights to use, copy, and modify the Software for personal use. 
Users are also allowed and encouraged to submit corrections and modifications 
to the Software for the benefit of other users.

It is not allowed to reuse,  modify, or redistribute the Software for 
commercial use in any way, or for a user's educational materials such as books 
or blog articles without prior permission from the copyright holder. 

The above copyright notice and this permission notice need to be included 
in all copies or substantial portions of the software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS OR APRESS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package com.apress.reactive.person.services;

import com.apress.reactive.person.Person;
import com.apress.reactive.person.PersonReactiveRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Service
public class PersonReactiveServiceImpl implements PersonReactiveService {

    private static Logger logger = LoggerFactory.getLogger(PersonReactiveServiceImpl.class);

    private final PersonReactiveRepo personRepository;

    public PersonReactiveServiceImpl(PersonReactiveRepo personRepository) {
        this.personRepository = personRepository;
    }

    //added the subscriber just to make sure our service works
    @Override
    public Mono<Person> findById(String id) {
        return personRepository.findById(id).doOnNext(p -> logger.info("Found person: {}", p));
    }

    @Override
    public Flux<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Mono<Person> save(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Mono<Void> update(String id, Mono<Person> personMono) {
       return personRepository.findById(id).doOnNext(original ->
           personMono.doOnNext(
                   updatedPerson -> {
                       original.setUsername(updatedPerson.getUsername());
                       original.setFirstName(updatedPerson.getFirstName());
                       original.setLastName(updatedPerson.getLastName());
                       personRepository.save(original);
                   })
       ).then(Mono.empty());
    }

    @Override
    public Mono<Void> delete(String id) {
        return personRepository.findById(id).doOnNext(personRepository::delete).then(Mono.empty());
    }
}
