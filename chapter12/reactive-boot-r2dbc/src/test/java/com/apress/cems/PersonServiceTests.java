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
package com.apress.cems;

import com.apress.cems.person.Person;
import com.apress.cems.person.PersonRepo;
import com.apress.cems.person.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class PersonServiceTests extends TestBase {
    @Autowired
    PersonService personService;

    @Autowired
    DatabaseClient databaseClient;

    @BeforeEach
    void setUp() {
        init();
    }

    @Test
    void shouldReadAllPersons() {
        personService.findAll()
                .as(StepVerifier::create)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldReturnSherlockById() {
        Person sherlock = createPerson(1L, "sherlock.holmes", "Sherlock", "Holmes", "dudu");
        personService.findById(1L).as(StepVerifier::create)
                .assertNext(sherlock::equals)
                .verifyComplete();
    }

    @Test
    void shouldReturnSherlockByFirstName() {
        Person sherlock = createPerson(1L, "sherlock.holmes", "Sherlock", "Holmes", "dudu");
        personService.findByFirstName("Sherlock").as(StepVerifier::create)
                .assertNext(sherlock::equals)
                .verifyComplete();
    }

    @Test
    void shouldReturnSherlockByUsername() {
        Person sherlock = createPerson(1L, "sherlock.holmes", "Sherlock", "Holmes", "dudu");
        personService.findByUsername("sherlock.holmes").as(StepVerifier::create)
                .assertNext(sherlock::equals)
                .verifyComplete();
    }
}
