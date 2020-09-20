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
package com.apress.reactive;

import com.apress.reactive.person.Person;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MongoReactiveApplicationTests {
    private static Logger logger = LoggerFactory.getLogger(MongoReactiveApplicationTests.class);

    @LocalServerPort
    private Integer port;

    private String baseUrl = "http://localhost";

    private WebTestClient webTestClient = null;

    @BeforeEach
    void setUp(){
        baseUrl = baseUrl.concat(":").concat(port.toString()).concat("/persons");
        webTestClient = WebTestClient
                .bindToServer()
                .baseUrl(baseUrl)
                .build();
    }

    @Order(1)
    @Test
    void shouldReturnAListOfPersons(){
        webTestClient.get().uri("/").accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Person.class).consumeWith(
                        listEntityExchangeResult ->
                        {
                            assertEquals(3, listEntityExchangeResult.getResponseBody().size());
                            listEntityExchangeResult.getResponseBody().forEach(p -> logger.info("All: {}",p));
                        }

        );
    }

    @Order(2)
    @Test
    void shouldReturnNoPerson(){
        webTestClient.get().uri("/99sdsd").accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Order(3)
    @Test
    void shouldCreateAPerson(){
        Person person = new Person();
        person.setUsername("catherine.cawood");
        person.setFirstName("Catherine");
        person.setLastName("Cawood");
        person.setPassword("ccwoo");
        person.setHiringDate(DateProcessor.toDate("1986-05-27 00:38"));

        webTestClient.post().uri("/").body(Mono.just(person), Person.class).exchange().expectStatus().isCreated()
                .expectBody(Person.class).consumeWith(responseEntity -> {
            Person p = responseEntity.getResponseBody();
            assertNotNull(p);
            assertAll("person",
                    () -> assertNotNull(p.getId()),
                    () -> assertEquals("Catherine", p.getFirstName()),
                    () -> assertEquals("Cawood", p.getLastName()));
                }
        );
    }

    @Order(4)
    @Test
    void shouldReturnAPerson() {
        webTestClient.get().uri("/gigipedala43").accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Person.class).consumeWith(responseEntity -> {
            Person person = responseEntity.getResponseBody();
            assertAll("person", () ->
            {
                assertNotNull(person);
                assertAll("person",
                        () -> assertEquals("Gigi", person.getFirstName()),
                        () -> assertEquals("Pedala", person.getLastName()));
            });

        });
    }

    @Order(5)
    @Test
    void shouldUpdateAPerson() {
        webTestClient.get().uri("/gigipedala43").accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Person.class).consumeWith(responseEntity -> {
            Person person = responseEntity.getResponseBody();
            Objects.requireNonNull(person).setFirstName("Gigi Lopata");
            webTestClient.put().uri("/1").body(Mono.just(person), Person.class).exchange().expectStatus().isNoContent();
        });
    }

    @Order(6)
    @Test
    void shouldDeleteAPerson() {
        webTestClient.delete().uri("/gigipedala43").exchange().expectStatus().isNoContent();
    }

}

class DateProcessor {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    static LocalDateTime toDate(final String date) {
        return LocalDateTime.parse(date, formatter);
    }
}

