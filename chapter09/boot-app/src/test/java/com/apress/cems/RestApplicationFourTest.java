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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@ActiveProfiles("four")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestApplicationFourTest {
    @Value("${local.management.port}")
    private int mgt;

    private String mgtUrl = "http://localhost";

    private static RestTemplate restTemplate = null;

    @BeforeAll
    static void init() {
        restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public boolean hasError(HttpStatus statusCode) {
                return false;
            }
        });
    }

    @BeforeEach
    void setUp(){
        mgtUrl = mgtUrl.concat(":").concat(mgt + "").concat("/actuator");
    }

    @Test
    void shouldReturn200WhenSendingRequestToShutdownEndpoint() {
        @SuppressWarnings("rawtypes")

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity postRequest = new HttpEntity(headers);
        ResponseEntity<Map>  entity = restTemplate.exchange(mgtUrl.concat("/shutdown"), HttpMethod.POST, postRequest, Map.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }


    @Test
    void shouldReturn200WhenSendingRequestToLoggerEndpoint() {
        @SuppressWarnings("rawtypes")

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var levelCfg = Map.of("configuredLevel", "DEBUG");
        HttpEntity<Map<String,String>> postRequest = new HttpEntity<>(levelCfg, headers);
        ResponseEntity<Map>  entity = restTemplate.exchange(mgtUrl.concat("/loggers/com.apress.cems.Initializer"), HttpMethod.POST, postRequest, Map.class);

        assertEquals(HttpStatus.NO_CONTENT, entity.getStatusCode());

        ResponseEntity<Map>  response = restTemplate.exchange(mgtUrl.concat("/loggers/com.apress.cems.Initializer"), HttpMethod.GET, postRequest, Map.class);
        assertEquals("DEBUG", Objects.requireNonNull(response.getBody()).get("configuredLevel"));
    }
}
