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
package com.apress.cems.aop;

import com.apress.cems.aop.config.AopConfig;
import com.apress.cems.aop.service.PersonService;
import com.apress.cems.aop.test.TestDbConfig;
import com.apress.cems.dao.Person;
import com.apress.cems.repos.PersonRepo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Disabled  // remove this to test your solution
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AopConfig.class, TestDbConfig.class})
class PersonMonitorTest {

    @Autowired
    PersonRepo personRepo;

    @Autowired
    PersonService personService;

    // can be used to test before, around and after advice
    @Test
    void testFindById() {
        personRepo.findById(1L).ifPresentOrElse(
                p -> assertEquals("sherlock.holmes", p.getUsername()),
                () -> fail("Person not found!")
        );
    }

    // can be used to test before, around and after advice
    @Test
    void testFindByCompleteName() {
        personService.findByCompleteName("Sherlock", "Holmes").ifPresent(person ->
                assertEquals("sherlock.holmes", person.getUsername())
        );
    }

    //this method does not test any advice because no pointcut expression matches it
    @Test
    void testFindAll() {
        assertNotNull(personService.findAll());
    }

    // can be used to test after-returning advice
    @Test
    void testSave() {
        var person = new Person();
        person.setId(3L);
        person.setUsername("nancy.drew");
        person.setFirstName("Nancy");
        person.setLastName("Drew");
        person.setPassword("1@#$asta");
        person.setHiringDate(LocalDateTime.now());
        assertNotNull(personService.save(person));
    }

    // can be used to test after-throwing advice (and before, around and after advice)
    @Test
    void testBadUpdate() {
        personRepo.findById(1L).ifPresentOrElse(
                p -> assertThrows(IllegalArgumentException.class, () -> personService.updateFirstName(p, "Sh$r1oc#")),
                () -> fail("Person not found!")
        );
    }

}
