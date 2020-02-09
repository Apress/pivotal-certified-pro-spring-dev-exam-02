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
package com.apress.cems.r2dbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

@Service
public class Initializer {
    private static Logger logger = LoggerFactory.getLogger(Initializer.class);

    Connection jdbc;

    @PostConstruct
    public void init() {
        logger.info(" -->> Starting database initialization...");
        try {
            jdbc = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;", "sa", "");
            Statement statement = jdbc.createStatement();
            List<String> statements = Arrays.asList(
                    "drop table PERSON if exists;",
                    "create table PERSON\n" +
                            "(\n" +
                            "  ID BIGINT IDENTITY PRIMARY KEY\n" +
                            ", LOGINUSER VARCHAR2(50) NOT NULL\n" +
                            ", FIRSTNAME VARCHAR2(50)\n" +
                            ", LASTNAME VARCHAR2(50)\n" +
                            ", PASSWORD VARCHAR2(50) NOT NULL\n" +
                            ", HIRINGDATE TIMESTAMP\n" +
                            ", VERSION INT\n" +
                            ", CREATEDAT TIMESTAMP NOT NULL\n" +
                            ", MODIFIEDAT TIMESTAMP NOT NULL\n" +
                            ", UNIQUE(LOGINUSER)\n" +
                            ");",
                    "INSERT INTO PERSON(ID, LOGINUSER, FIRSTNAME, LASTNAME, PASSWORD, HIRINGDATE, VERSION, CREATEDAT, MODIFIEDAT) VALUES (1, 'sherlock.holmes', 'Sherlock', 'Holmes', '123ss12sh', '1983-08-18 00:01', 1, '1983-08-18 00:01', '1999-03-18 00:02' );" ,
                    "INSERT INTO PERSON(ID, LOGINUSER, FIRSTNAME, LASTNAME, PASSWORD, HIRINGDATE, VERSION, CREATEDAT, MODIFIEDAT) VALUES (2, 'jackson.brodie', 'Jackson', 'Brodie', '12wsed34', '1990-08-18 00:03', 1, '1990-07-18 00:04', '1990-07-18 00:05');",
                    "INSERT INTO PERSON(ID, LOGINUSER, FIRSTNAME, LASTNAME, PASSWORD, HIRINGDATE, VERSION, CREATEDAT, MODIFIEDAT) VALUES (3, 'gigi.pedala', 'Gigi', 'Pedala', 'id12dvd3ds', '1990-08-18 00:03', 1, '1990-07-18 00:04', '1990-07-18 00:05');"
            );
            statements.forEach(st -> {
                try {
                    statement.execute(st);
                } catch (SQLException e) {
                    logger.error("Could not execute statement '{}'.", st, e);
                }
            });
        } catch (SQLException e) {
            logger.error("Could not initialize database", e);
        }
        logger.info(" -->> Database initialization finished.");
    }
}
