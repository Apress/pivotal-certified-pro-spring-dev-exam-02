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
package com.apress.cems.reactive.person;

import com.apress.cems.person.Person;
import com.apress.cems.util.NumberGenerator;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Service
public class ReactivePersonRepoImpl implements ReactivePersonRepo {

    private PersonRepo personRepo;

    public ReactivePersonRepoImpl(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @Override public Mono<Person> findById(Long id) {
        return Mono.justOrEmpty(personRepo.findById(id));
    }

    @Override public Flux<Person> findAll() {
        return Flux.fromIterable(personRepo.findAll());
    }

    @Override public Mono<Person> save(Mono<Person> personMono) {
        return personMono.doOnNext(person ->  {
            if(StringUtils.isEmpty(person.getPassword())){
                person.setPassword(NumberGenerator.getPassword());
            }
            Mono.just(personRepo.save(person));
        });
    }

    @Override public Mono<Void> update(Long id, Mono<Person> personMono) {
        Optional<Person> personOpt = personRepo.findById(id);
        if(personOpt.isPresent()) {
            Person original = personOpt.get();
            return personMono.doOnNext(
                    updatedPerson -> {
                        original.setUsername(updatedPerson.getUsername());
                        original.setFirstName(updatedPerson.getFirstName());
                        original.setLastName(updatedPerson.getLastName());
                        personRepo.save(original);
                    }
            ).thenEmpty(Mono.empty());
        }
        return Mono.empty();
    }

    @Override
    public Mono<Void> delete(Long id) {
        personRepo.findById(id).ifPresent(person -> personRepo.delete(person));
        return Mono.empty();
    }
}
