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
package com.apress.cems.r2dbc.person.services;

import com.apress.cems.r2dbc.person.Person;
import com.apress.cems.r2dbc.person.PersonRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService {
    private PersonRepo personRepo;

    public PersonServiceImpl(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @Transactional(readOnly = true)
    @Override
    public Flux<Person> findAll() {
        return personRepo.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Mono<Person> findById(Long id) {
        return personRepo.findById(id);
    }

    @Override
    public Mono<Person> save(Person person) {
        return personRepo.save(person);
    }

    @Override
    public Mono<Void> update(Long id, Mono<Person> personMono) {
        return personRepo.findById(id).doOnNext(original ->
                personMono.doOnNext(
                        updatedPerson -> {
                            original.setLoginuser(updatedPerson.getLoginuser());
                            original.setFirstname(updatedPerson.getFirstname());
                            original.setLastname(updatedPerson.getLastname());
                            personRepo.save(original);
                        })
        ).then(Mono.empty());
    }

    @Override
    public Mono<Void> delete(Long id) {
        return personRepo.findById(id).doOnNext(personRepo::delete).then(Mono.empty());
    }

    @Transactional(readOnly = true)
    @Override
    public Mono<Person> findByLoginuser(String loginuser) {
        return personRepo.findByLoginuser(loginuser);
    }

    @Transactional(readOnly = true)
    @Override
    public Flux<Person> findByFirstname(String firstName) {
        return personRepo.findByFirstname(firstName);
    }
}
