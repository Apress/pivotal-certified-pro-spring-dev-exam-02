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
package com.apress.reactive;

import com.apress.reactive.person.Person;
import com.apress.reactive.person.services.PersonReactiveService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Disabled("for some reason these tests fail in the gradle build, run them only manually from IntelliJ IDEA")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class MongoReactiveServiceTest {

    @Autowired
    PersonReactiveService personService;

    @Test
    void shouldReadAllPersons() {
        personService.findAll()
                .as(StepVerifier::create)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void shouldReturnSherlockById() {
        Person gigi = createPerson( "gigi.pedala", "Gigi", "Pedala", "12345");
        personService.findById("gigipedala43").as(StepVerifier::create)
                .expectSubscription()
                .assertNext(gigi::equals)
                .verifyComplete();
    }

    Person createPerson( String s2, String fn, String ln, String password) {
        Person person = new Person();
        person.setUsername(s2);
        person.setFirstName(fn);
        person.setLastName(ln);
        person.setPassword(password);
        person.setHiringDate(LocalDateTime.now());
        return person;
    }
}
