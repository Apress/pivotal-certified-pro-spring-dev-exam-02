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

import com.apress.cems.detective.Detective;
import com.apress.cems.detective.services.DetectiveService;
import com.apress.cems.person.Person;
import com.apress.cems.person.services.PersonService;
import com.apress.cems.util.DateProcessor;
import com.apress.cems.util.EmploymentStatus;
import com.apress.cems.util.NumberGenerator;
import com.apress.cems.util.Rank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class Initializer {
    private Logger logger = LoggerFactory.getLogger(Initializer.class);

    private PersonService personService;

    private DetectiveService detectiveService;

    public Initializer(PersonService personService, DetectiveService detectiveService) {
        this.personService = personService;
        this.detectiveService = detectiveService;
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
        person.setHiringDate(DateProcessor.toDate("1983-08-15 00:25"));
        personService.save(person);
        Detective detective = createDetective(person, Rank.INSPECTOR, false, EmploymentStatus.ACTIVE);
        detectiveService.save(detective);

        person = new Person();
        person.setUsername("jackson.brodie");
        person.setFirstName("Jackson");
        person.setLastName("Brodie");
        person.setPassword("bagy");
        person.setHiringDate(DateProcessor.toDate("1983-06-22 00:25"));
        personService.save(person);
        detective = createDetective(person, Rank.SENIOR, true, EmploymentStatus.ACTIVE);
        detectiveService.save(detective);

        person = new Person();
        person.setUsername("nancy.drew");
        person.setFirstName("Nancy");
        person.setLastName("Drew");
        person.setPassword("dada45");
        person.setHiringDate(DateProcessor.toDate("1990-05-21 00:25"));
        personService.save(person);
        detective = createDetective(person, Rank.TRAINEE, false, EmploymentStatus.VACATION);
        detectiveService.save(detective);

        person = new Person();
        person.setUsername("irene.adler");
        person.setFirstName("Irene");
        person.setLastName("Adler");
        person.setPassword("xxxyy");
        person.setHiringDate(DateProcessor.toDate("1987-03-11 00:25"));
        personService.save(person);
        detective = createDetective(person, Rank.INSPECTOR, true, EmploymentStatus.SUSPENDED);
        detectiveService.save(detective);
    }

    private Detective createDetective(Person person, Rank rank, Boolean armed, EmploymentStatus status){
        Detective detective = new Detective();
        detective.setPerson(person);
        detective.setBadgeNumber(NumberGenerator.getBadgeNumber());
        detective.setRank(rank);
        detective.setArmed(armed);
        detective.setStatus(status);
        return detective;
    }
}