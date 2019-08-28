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
package com.apress.reactive.person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Service
public class Initializer {
    private static Logger logger = LoggerFactory.getLogger(Initializer.class);

    private final PersonReactiveRepo personRepository;

    public Initializer(PersonReactiveRepo personRepository) {
        this.personRepository = personRepository;
    }

    @PostConstruct
    void init(){
        Person person1 = new Person();
        person1.setId(UUID.randomUUID().toString());
        person1.setUsername("sherlock.holmes");
        person1.setFirstName("Sherlock");
        person1.setLastName("Holmes");
        person1.setPassword("dudu");
        person1.setHiringDate(LocalDateTime.now());

        Person person2 = new Person();
        person2.setId(UUID.randomUUID().toString());
        person2.setUsername("jackson.brodie");
        person2.setFirstName("Jackson");
        person2.setLastName("Brodie");
        person2.setPassword("bagy");
        person2.setHiringDate(LocalDateTime.now());

        //need this for find/update/delete
        Person person3 = new Person();
        person3.setId("gigipedala43");
        person3.setUsername("gigi.pedala");
        person3.setFirstName("Gigi");
        person3.setLastName("Pedala");
        person3.setPassword("12345");
        person3.setHiringDate(LocalDateTime.now());

        personRepository.deleteAll().thenMany(Flux.just(person1,person2,person3).flatMap(personRepository::save))
                .thenMany(personRepository.findAll()).subscribe(p -> logger.info("Person found: {}", p));

    }
}
