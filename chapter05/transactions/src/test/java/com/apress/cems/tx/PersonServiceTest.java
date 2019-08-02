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
package com.apress.cems.tx;

import com.apress.cems.aop.exception.MailSendingException;
import com.apress.cems.aop.service.PersonService;
import com.apress.cems.tx.config.AppConfig;
import com.apress.cems.tx.config.TestTransactionalDbConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestTransactionalDbConfig.class, AppConfig.class})
 class PersonServiceTest {
    private Logger logger = LoggerFactory.getLogger(PersonServiceTest.class);

    @Autowired
    PersonService personService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    @SqlGroup({
            @Sql(value = "classpath:test/extra-data.sql", config = @SqlConfig(encoding = "utf-8", separator = ";", commentPrefix = "--")),
            @Sql(
                    scripts = "classpath:test/delete-test-data.sql",
                    config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
            )
    })
     void testCount() {
        long count = personService.countPersons();
        assertEquals(4, count);
    }

    @Test
    @Sql(statements = {"drop table NEW_PERSON if exists;"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testCreateTable(){
        jdbcTemplate.execute("create table NEW_PERSON(" +
                "  ID BIGINT IDENTITY PRIMARY KEY " +
                ", USERNAME VARCHAR2(50) NOT NULL " +
                ", FIRSTNAME VARCHAR2(50) " +
                ", LASTNAME VARCHAR2(50) " +
                ", UNIQUE(USERNAME)) ");
        long result = jdbcTemplate.queryForObject("select count(*) from NEW_PERSON", Long.class);
        // table exists but is empty
        assertEquals(0, result);
    }

    @Test
    void testUpdatePassword() {
        personService.findById(2L).ifPresent(
                p ->
                        assertThrows(
                                MailSendingException.class,
                                () -> personService.updatePassword(p, "test_pass")
                        )
        );
    }

    @Test
    void testUpdateUsername() {
        personService.findById(1L).ifPresent(
                p ->
                        assertThrows(
                                DataIntegrityViolationException.class,
                                () -> personService.updateUsername(p, "irene.adler")
                        )
        );
        //making sure rollback did not affect anything
        personService.findById(1L).ifPresent(p -> logger.info("->> {}" , p.toString()));
    }
}
