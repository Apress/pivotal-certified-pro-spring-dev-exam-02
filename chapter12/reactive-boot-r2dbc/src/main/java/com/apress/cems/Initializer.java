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
import io.r2dbc.spi.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Service
public class Initializer {

    private static Logger logger = LoggerFactory.getLogger(Initializer.class);

    private DatabaseClient databaseClient;

    private Initializer(ConnectionFactory connectionFactory) {
        databaseClient = DatabaseClient.create(connectionFactory);
    }

    @PostConstruct
    private void init(){
        databaseClient.execute()
                .sql("create table PERSON\n" +
                        "(\n" +
                        "  ID BIGINT IDENTITY PRIMARY KEY\n" +
                        ", USERNAME VARCHAR2(50) NOT NULL\n" +
                        ", FIRSTNAME VARCHAR2(50)\n" +
                        ", LASTNAME VARCHAR2(50)\n" +
                        ", PASSWORD VARCHAR2(50) NOT NULL\n" +
                        ", HIRINGDATE TIMESTAMP\n" +
                        ", VERSION INT\n" +
                        ", CREATED_AT TIMESTAMP NOT NULL\n" +
                        ", MODIFIED_AT TIMESTAMP NOT NULL\n" +
                        ", UNIQUE(USERNAME)\n" +
                        ")")
                .fetch()
                .rowsUpdated().doOnNext(count -> logger.info("{} table created.", count));

        databaseClient.insert()
                .into(Person.class)
                .using(createPerson(1L, "sherlock.holmes", "Sherlock", "Holmes", "dudu"));

        databaseClient.insert()
                .into(Person.class)
                .using(createPerson(2L, "jackson.brodie", "Jackson", "Brodie", "bagy"));

    }

    private Person createPerson(Long id, String s2, String sherlock, String holmes, String dudu) {
        Person person = new Person();
        person.setId(id);
        person.setUsername(s2);
        person.setFirstName(sherlock);
        person.setLastName(holmes);
        person.setPassword(dudu);
        person.setHiringDate(LocalDateTime.now());
        return person;
    }

}
