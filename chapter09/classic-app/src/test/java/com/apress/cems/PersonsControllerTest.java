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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.http.*;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDateTime;

import static com.apress.cems.util.NumberGenerator.randomCharacter;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Disabled
class PersonsControllerTest {

    private static String BASE_URL = "http://localhost:8080/classic-app/";

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

    @RepeatedTest(3000)
    void shouldReturnAListOfPersons(){
        Person[] persons = restTemplate.getForObject(BASE_URL.concat("/persons"), Person[].class);
        assertAll(
                () -> assertNotNull(persons),
                () -> assertTrue(persons.length >= 4)
        );
    }

    @RepeatedTest(3000)
    void shouldCreateAPerson() {
        Person person = buildPerson();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity<Person> postRequest = new HttpEntity<>(person, headers);
        URI uri = restTemplate.postForLocation(BASE_URL.concat("/persons"), postRequest, Person.class);

        assertNotNull(uri);
        Person newPerson = restTemplate.getForObject(uri, Person.class);
        assertAll(
                () -> assertNotNull(newPerson),
                () -> assertEquals(person.getUsername(), newPerson.getUsername()),
                () -> assertNotNull(newPerson.getId())
        );
    }

    private Person buildPerson() {
        Person person = new Person();
        person.setUsername(genString(15));
        person.setFirstName(genString(15));
        person.setLastName(genString(15));
        person.setPassword(genString(15));
        person.setHiringDate(LocalDateTime.now());
        return person;
    }

    private String genString(int size) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; ++i) {
            sb.append(randomCharacter());
        }
        return sb.toString();
    }
}
