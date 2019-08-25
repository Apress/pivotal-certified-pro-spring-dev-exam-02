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
package com.apress.cems.boot;

import com.apress.cems.boot.dao.Person;
import com.apress.cems.boot.repos.PersonRepo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@DataJpaTest
public class RepositoriesTest {

    @Autowired
    PersonRepo personRepo;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void testsavePerson(){
        var person = new  Person();
        person.setUsername("irene.adler");
        person.setFirstName("Irene");
        person.setLastName("Adler");
        person.setHiringDate(LocalDateTime.now());
        person.setPassword("a12sd");

        var savedPerson = personRepo.save(person);

        assertAll(
                () ->   assertNotNull(savedPerson.getId()),
                () ->   assertNotNull(savedPerson.getCreatedAt()),
                () ->   assertEquals(person,savedPerson)
        );
    }

    @Test
    void testFindByCompleteName() {
        Person person = new  Person();
        person.setUsername("irene.adler");
        person.setFirstName("Irene");
        person.setLastName("Adler");
        person.setHiringDate(LocalDateTime.now());
        person.setPassword("a12sd");

        entityManager.persist(person);
        entityManager.flush();

        personRepo.findByCompleteName("Irene", "Adler").ifPresentOrElse(
                foundPerson -> assertEquals(person, foundPerson),
                () -> fail("Person not found!")
        );
    }
}
