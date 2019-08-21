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
package com.apress.cems.sec;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@ActiveProfiles("one")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SecuredRestApplicationTest {
    private static TestRestTemplate testRestTemplate = null;

    @BeforeAll
    static void init() {
        testRestTemplate = new TestRestTemplate("john", "doe");
    }


    @Value("${local.management.port}")
    private int mgt;

    private String mgtUrl = "http://localhost";


    @BeforeEach
    void setUp(){
        mgtUrl = mgtUrl.concat(":").concat(mgt + "").concat("/actuator");
    }

    @Test
    void shouldReturn200WhenSendingRequestToInfoEndpointForUSER() {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = testRestTemplate.getForEntity(
                mgtUrl.concat("/info"), Map.class);

        @SuppressWarnings("unchecked")
        Map<String, String> content = (Map<String, String>) entity.getBody().get("app");

        assertAll(
                () -> assertEquals(HttpStatus.OK, entity.getStatusCode()),
                () -> assertEquals(3, content.size()),
                () -> assertEquals("Spring Actuator Secured Application", content.get("name")),
                () -> assertEquals("1.0-SNAPSHOT", content.get("version"))
        );
    }
}
