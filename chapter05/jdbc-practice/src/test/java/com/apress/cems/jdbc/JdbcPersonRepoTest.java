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
package com.apress.cems.jdbc;

import com.apress.cems.dao.Person;
import com.apress.cems.jdbc.config.TestDbConfig;
import com.apress.cems.repos.PersonRepo;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Disabled // delete this line to execute this test class
@SpringJUnitConfig(classes = {TestDbConfig.class, JdbcConfig.class})
class JdbcPersonRepoTest {

    private Logger logger = LoggerFactory.getLogger(JdbcPersonRepoTest.class);

    static final Long PERSON_ID = 1L;

    @Autowired
    @Qualifier("extraJdbcPersonRepo")
    PersonRepo personRepo;

    @BeforeEach
    void setUp(){
        assertNotNull(personRepo);
    }

    @Test
    void testFindByIdPositive(){
        personRepo.findById(PERSON_ID).ifPresentOrElse(
                p -> assertEquals("Sherlock", p.getFirstName()),
                Assertions:: fail
        );
    }

    @Test
    public void testUpdate(){
        int result  = personRepo.updatePassword(1L, "newpass");
        assertEquals(1, result);
    }

    @Test
    void testFindByIdNegative(){
        // TODO 26: Use the JdbcTemplate instance to query for a person that does not exist and make this test pass
    }

    @Test
    void testCountPersons(){
        Set<Person> personSet = personRepo.findAll();
        assertNotNull(personSet);
        assertEquals(2, personSet.size());
    }

    @Test
    void testFindAll(){
        int result = 0;
        // TODO 27: Use the JdbcTemplate instance to query for the number of rows in the PERSON table
        assertEquals(2, result);
    }

    @Test
    void testFindAsMap(){
        Map<String, Object> result  = personRepo.findByIdAsMap(PERSON_ID);
        assertEquals(9, result.size());
        logger.info("Res: {}", result);
    }

    @Test
    void testFindAsList(){
        List<Map<String, Object>> result  = personRepo.findAllAsMaps();
        assertEquals(2, result.size());
        logger.info("Res: {}", result);
    }

    @Test
     void testCreatePerson(){
        int result  = personRepo.createPerson(3L, "chloe.decker", "Chloe", "Decker", "m0rn1ngstar");
        assertEquals(1, result);

        Optional<Person> personOpt = personRepo.findByUsername("chloe.decker");
        personOpt.ifPresentOrElse(p -> assertNotNull(p.getId()),
                Assertions:: fail);
    }

    @Test
    void testPrintHtml(){
       personRepo.htmlAllByName("sherlock.holmes");
    }

    @AfterEach
    void cleanUp(){
        try {
            personRepo.findByUsername("chloe.decker").ifPresent(p -> personRepo.delete(p));
        } catch (EmptyResultDataAccessException e){
            // do nothing we expect this
        }
    }
}
