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
package com.apress.cems.hib;

import com.apress.cems.dao.Person;
import com.apress.cems.hib.config.AppConfig;
import com.apress.cems.hib.config.HibernateDbConfig;
import com.apress.cems.repos.PersonRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateDbConfig.class, AppConfig.class})
@Transactional
@Disabled
class HibernateRepoTest {

    @Autowired
    @Qualifier("hibernatePersonRepo")
    PersonRepo personRepo;

    @Test
    void testNativeQuery(){
        List<String> usernames = personRepo.findAllUsernames();
        assertEquals(2,usernames.size());
    }

    @Test
    void testFindAllByLastName() {
        List<Person> persons = personRepo.findAllByLastName("Holmes");
        assertEquals(1,persons.size());
    }

    @Test
    void testCountPersons(){
        assertEquals(2, personRepo.count());
    }

    @Test
    void testDeleteById(){
        personRepo.deleteById(1L);

        personRepo.findById(1L).ifPresent(p -> fail("Person found!"));
    }
}
