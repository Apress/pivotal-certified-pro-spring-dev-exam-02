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
package com.apress.cems.dj;

import com.apress.cems.dj.config.DataSourceConfig;
import com.apress.cems.dj.services.DetectiveService;
import com.apress.cems.dj.services.PersonService;

import com.apress.cems.dj.services.StorageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DataSourceConfig.class, ServiceConfig.class})
 class PersonServiceTest {

    @Autowired
    PersonService personService;

    @Autowired
    StorageService storageService;

    @Autowired
    DetectiveService detectiveService;

    @Test
    void testFindById() {
        // because 1L is the Storage
        personService.findById(2L).ifPresentOrElse(
                p -> assertEquals("sherlock.holmes", p.getUsername()),
                () -> fail("Person not found!")
        );
    }

    @Test
    void testfindByCompleteName() {
        personService.findByCompleteName("Sherlock", "Holmes").ifPresent(person ->
                assertEquals("sherlock.holmes", person.getUsername())
        );
    }

    @Test
    void testFindAll() {
        assertNotNull(personService.findAll());
    }


}
