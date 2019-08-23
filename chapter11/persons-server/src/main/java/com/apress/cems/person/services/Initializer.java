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
package com.apress.cems.person.services;

import com.apress.cems.person.Person;
import com.apress.cems.util.DateProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Service
@Transactional
public class Initializer {
    private Logger logger = LoggerFactory.getLogger(Initializer.class);

    private PersonService personService;

    public Initializer(PersonService personService) {
        this.personService = personService;
    }

    @PostConstruct
    public void init() {
        logger.info(" -->> Starting database initialization...");
        if (personService.findAll().isEmpty()) {
            createPersons();
        }
        logger.info(" -->> Database initialization finished.");
    }

    private void createPersons() {
        Person person = new Person();
        person.setUsername("sherlock.holmes");
        person.setFirstName("Sherlock");
        person.setLastName("Holmes");
        person.setPassword("dudu");
        person.setHiringDate(DateProcessor.toDate("1983-08-15 00:23"));
        personService.save(person);

        person = new Person();
        person.setUsername("jackson.brodie");
        person.setFirstName("Jackson");
        person.setLastName("Brodie");
        person.setPassword("bagy");
        person.setHiringDate(DateProcessor.toDate("1983-06-22 00:23"));
        personService.save(person);

        person = new Person();
        person.setUsername("nancy.drew");
        person.setFirstName("Nancy");
        person.setLastName("Drew");
        person.setPassword("dada45");
        person.setHiringDate(DateProcessor.toDate("1990-05-21 00:23"));
        personService.save(person);

        person = new Person();
        person.setUsername("irene.adler");
        person.setFirstName("Irene");
        person.setLastName("Adler");
        person.setPassword("xxxyy");
        person.setHiringDate(DateProcessor.toDate("1987-03-11 00:23"));
        personService.save(person);
    }
}