package com.apress.cems.r2dbc;

import com.apress.cems.r2dbc.person.Person;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ReactiveBootR2dbcApplication.class)
public class R2dbcApplicationTests extends TestBase {
    private static Logger logger = LoggerFactory.getLogger(R2dbcApplicationTests.class);

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
                    assertEquals(3, Objects.requireNonNull(listEntityExchangeResult.getResponseBody()).size());
                    listEntityExchangeResult.getResponseBody().forEach(p -> logger.info("All: {}",p));
                }
        );
    }

    @Order(2)
    @Test
    void shouldReturnNoPerson(){
        webTestClient.get().uri("/99").accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Order(3)
    @Test
    void shouldCreateAPerson(){
        Person person = createPerson(null, "catherine.cawood", "Catherine", "Cawood", "ccwoo");

        webTestClient.post().uri("/").body(Mono.just(person), Person.class).exchange().expectStatus().isCreated()
                .expectBody(Person.class).consumeWith(responseEntity -> {
                    Person p = responseEntity.getResponseBody();
                    assertNotNull(p);
                    assertAll("person",
                            () -> assertNotNull(p.getId()),
                            () -> assertEquals("Catherine", p.getFirstname()),
                            () -> assertEquals("Cawood", p.getLastname()));
                }
        );
    }

    @Order(4)
    @Test
    void shouldReturnAPerson() {
        webTestClient.get().uri("/3").accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Person.class).consumeWith(responseEntity -> {
            Person person = responseEntity.getResponseBody();
            assertAll("person", () ->
            {
                assertNotNull(person);
                assertAll("person",
                        () -> assertEquals("Gigi", person.getFirstname()),
                        () -> assertEquals("Pedala", person.getLastname()));
            });

        });
    }

    @Order(5)
    @Test
    void shouldUpdateAPerson() {
        webTestClient.get().uri("/3").accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Person.class).consumeWith(responseEntity -> {
            Person person = responseEntity.getResponseBody();
            person.setFirstname("Gigi Lopata");
            webTestClient.put().uri("/3").body(Mono.just(person), Person.class).exchange().expectStatus().isNoContent();
        });
    }

    @Order(5)
    @Test
    void shouldDeleteAPerson() {
        webTestClient.delete().uri("/3").exchange().expectStatus().isNoContent();
    }

}
