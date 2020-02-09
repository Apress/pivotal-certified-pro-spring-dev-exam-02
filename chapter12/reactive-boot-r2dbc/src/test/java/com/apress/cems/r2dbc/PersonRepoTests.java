package com.apress.cems.r2dbc;

import com.apress.cems.r2dbc.person.Person;
import com.apress.cems.r2dbc.person.PersonRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ReactiveBootR2dbcApplication.class)
class PersonRepoTests extends TestBase {

	@Autowired
    PersonRepo personRepo;

	@Test
	void shouldReadAllPersons() {
		personRepo.findAll()
				.as(StepVerifier::create)
				.expectNextCount(3)
				.verifyComplete();
	}

	@Test
	void shouldReturnSherlockById() {
		Person sherlock = createPerson(1L, "sherlock.holmes", "Sherlock", "Holmes", "dudu");
		personRepo.findById(1L).as(StepVerifier::create)
				.assertNext(sherlock::equals)
				.verifyComplete();
	}

	@Test
	void shouldReturnSherlockByFirstName() {
		Person sherlock = createPerson(1L, "sherlock.holmes", "Sherlock", "Holmes", "dudu");
		personRepo.findByFirstname("Sherlock").as(StepVerifier::create)
				.assertNext(sherlock::equals)
				.verifyComplete();
	}

	@Test
	void shouldReturnSherlockByUsername() {
		Person sherlock = createPerson(1L, "sherlock.holmes", "Sherlock", "Holmes", "dudu");
		personRepo.findByLoginuser("sherlock.holmes").as(StepVerifier::create)
				.assertNext(sherlock::equals)
				.verifyComplete();
	}

}
