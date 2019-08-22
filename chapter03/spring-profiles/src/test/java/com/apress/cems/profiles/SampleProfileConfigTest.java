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
package com.apress.cems.profiles;

import com.apress.cems.repos.DetectiveRepo;
import com.apress.cems.repos.PersonRepo;
import com.apress.cems.repos.impl.JdbcDetectiveRepo;
import com.apress.cems.repos.impl.JdbcPersonRepo;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
//@ActiveProfiles("one")
//@ActiveProfiles("two")
@SpringJUnitConfig(classes = SampleProfileConfigTest.TestCtxConfig.class)
class SampleProfileConfigTest {

    private Logger logger = LoggerFactory.getLogger(SampleProfileConfigTest.class);

    @Autowired
    ApplicationContext ctx;

    @Test
    void testBean(){
        Arrays.stream(ctx.getBeanDefinitionNames()).forEach(beanName -> logger.info(beanName));
    }

    @Configuration
    static class TestCtxConfig {

        @Bean
        @Profile("one")
        PersonRepo jdbcPersonRepo() {
            return new JdbcPersonRepo(jdbcTemplate());
        }

        @Bean
        @Profile("two")
        DetectiveRepo jdbcDetectiveRepo(){
            return new JdbcDetectiveRepo(jdbcTemplate());
        }

        @Bean
        JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource());
        }

        @Bean
        public DataSource dataSource() {
            var builder = new EmbeddedDatabaseBuilder();
            var db = builder
                    .setType(EmbeddedDatabaseType.H2)
                    .generateUniqueName(true)
                    .build();
            return db;
        }
    }
}
