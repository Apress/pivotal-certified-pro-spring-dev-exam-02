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
package com.apress.cems.mongo;

import com.apress.cems.mongo.dao.Person;
import com.apress.cems.mongo.services.PersonService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@SpringBootTest
class PersonServiceTest {

    private Logger logger = LoggerFactory.getLogger(PersonServiceTest.class);

    @Autowired
    PersonService personService;

    @BeforeEach
    void setUp(){
        assertNotNull(personService);
        init();
    }

    @AfterEach
    void tearDown(){
        personService.deleteAll();
    }

    @Test
    void testFindByLastName(){
        List<Person> persons = personService.findByLastName("Holmes");
        assertEquals(1, persons.size());
    }

    @Test
    void testFindByUsername(){
        Person person = personService.findByUsername("sherlock.holmes");
        assertNotNull(person);
        logger.info("Sherlock {}" , person);
    }

    @Test
    void testFindAll() {
        List<Person> persons = personService.findAll();
        assertEquals(2, persons.size());
    }

    void init() {
        logger.info(" -->> Starting database initialization...");
        Person person = new Person();
        person.setUsername("sherlock.holmes");
        person.setFirstName("Sherlock");
        person.setLastName("Holmes");
        person.setPassword("dudu");
        person.setHiringDate(LocalDateTime.now());
        personService.save(person);

        person = new Person();
        person.setUsername("jackson.brodie");
        person.setFirstName("Jackson");
        person.setLastName("Brodie");
        person.setPassword("bagy");
        person.setHiringDate(LocalDateTime.now());
        personService.save(person);
        logger.info(" -->> Database initialization finished.");

    }
}
