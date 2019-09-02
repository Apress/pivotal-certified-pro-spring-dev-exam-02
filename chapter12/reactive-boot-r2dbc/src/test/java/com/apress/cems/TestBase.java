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

import com.apress.cems.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public abstract class TestBase {

    @Autowired
    protected DatabaseClient databaseClient;

    protected void init(){
        List<String> statements = Arrays.asList(//
                "drop table PERSON if exists;",
                "create table PERSON\n" +
                        "(\n" +
                        "  ID BIGINT IDENTITY PRIMARY KEY\n" +
                        ", USERNAME VARCHAR2(50) NOT NULL\n" +
                        ", FIRSTNAME VARCHAR2(50)\n" +
                        ", LASTNAME VARCHAR2(50)\n" +
                        ", PASSWORD VARCHAR2(50) NOT NULL\n" +
                        ", HIRINGDATE TIMESTAMP\n" +
                        ", VERSION INT\n" +
                        ", CREATEDAT TIMESTAMP NOT NULL\n" +
                        ", MODIFIEDAT TIMESTAMP NOT NULL\n" +
                        ", UNIQUE(USERNAME)\n" +
                        ");");

        statements.forEach(it -> databaseClient.execute(it)
                .fetch()
                .rowsUpdated()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete());

        databaseClient.insert()
                .into(Person.class)
                .using(createPerson(1L, "sherlock.holmes", "Sherlock", "Holmes", "dudu"))
                .then()
                .as(StepVerifier::create)
                .verifyComplete();

        databaseClient.insert()
                .into(Person.class)
                .using(createPerson(2L, "jackson.brodie", "Jackson", "Brodie", "bagy"))
                .then()
                .as(StepVerifier::create)
                .verifyComplete();

        databaseClient.insert()
                .into(Person.class)
                .using(createPerson(3L, "gigi.pedala", "Gigi", "Pedala", "dooooh"))
                .then()
                .as(StepVerifier::create)
                .verifyComplete();
    }

    protected Person createPerson(Long id, String s2, String sherlock, String holmes, String dudu) {
        Person person = new Person();
        person.setId(id);
        person.setUsername(s2);
        person.setFirstname(sherlock);
        person.setLastname(holmes);
        person.setPassword(dudu);
        person.setHiringdate(LocalDateTime.now());
        return person;
    }
}
