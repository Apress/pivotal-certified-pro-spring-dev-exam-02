package com.apress.cems.r2dbc;

import com.apress.cems.r2dbc.person.Person;
import com.apress.cems.r2dbc.person.services.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ReactiveBootR2dbcApplication.class)
public class PersonServiceTests extends TestBase {
    @Autowired
    PersonService personService;

    @Test
    void shouldReadAllPersons() {
        personService.findAll()
                .as(StepVerifier::create)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void shouldReturnSherlockById() {
        Person sherlock = createPerson(1L, "sherlock.holmes", "Sherlock", "Holmes", "dudu");
        personService.findById(1L).as(StepVerifier::create)
                .assertNext(sherlock::equals)
                .verifyComplete();
    }

    @Test
    void shouldReturnSherlockByFirstName() {
        Person sherlock = createPerson(1L, "sherlock.holmes", "Sherlock", "Holmes", "dudu");
        personService.findByFirstname("Sherlock").as(StepVerifier::create)
                .assertNext(sherlock::equals)
                .verifyComplete();
    }

    @Test
    void shouldReturnSherlockByUsername() {
        Person sherlock = createPerson(1L, "sherlock.holmes", "Sherlock", "Holmes", "dudu");
        personService.findByLoginuser("sherlock.holmes").as(StepVerifier::create)
                .assertNext(sherlock::equals)
                .verifyComplete();
    }
}
