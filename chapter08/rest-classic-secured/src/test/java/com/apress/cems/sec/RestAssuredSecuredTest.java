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

import com.apress.cems.dao.Person;
import com.apress.cems.util.DateProcessor;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 * Description: Create a launcher for this application with context rest-classic and port 8080. Then enable this test and run it.
 */
@Disabled
class RestAssuredSecuredTest {

    @BeforeEach
    void setupURL() {
        // setup the default URL and API base path to use throughout the tests
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/rest-classic-secured/persons";
    }

    @Test
    void shouldReturnAListOfPersons() {
        List<Person> personList =
                given().auth().preemptive()
                        .basic("jane", "doe")
                .when().get("/")
                .then()
                .assertThat().statusCode(HttpStatus.OK.value())
                .and()
                .contentType(ContentType.JSON)
                .and()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract().response().body().jsonPath().getList("$");
        assertNotNull(personList);
        assertTrue(personList.size() >= 4);
    }

    @Test
    void shouldUpdateAPerson() {
        Person person = buildPerson("sherlock.holmes", "Sherlock Cornelius", "Holmes", "complicated");

        given().auth().preemptive()
                .basic("jane", "doe")
                .contentType(ContentType.JSON)
                .body(person)
                .log().all()
                .when().put("/2")
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.NO_CONTENT.value());

    }

    @Test
    void shouldNotUpdateAPerson403() {
        Person person = buildPerson("sherlock.holmes", "Sherlock Cornelius", "Holmes", "complicated");

        given().auth().preemptive()
                .basic("john", "doe")
                .contentType(ContentType.JSON)
                .body(person)
                .when().put("/2")
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.FORBIDDEN.value());

    }

    @Test
    void shouldCreateAPerson() {
        Person person = new Person();
        person.setUsername("gigi.pedala");
        person.setFirstName("Gigi");
        person.setLastName("Pedala");
        person.setPassword("doohbeer");
        person.setHiringDate(LocalDateTime.now());

        given().auth().preemptive()
                .basic("jane", "doe")
                .contentType(ContentType.JSON)
                .body(person)
                .when()
                .post("/")
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.CREATED.value())
                .and()
                .contentType(ContentType.JSON)
                .assertThat().body("username", equalTo(person.getUsername()))
                .assertThat().body("firstName", equalTo(person.getFirstName()))
                .assertThat().body("lastName", equalTo(person.getLastName()))
                .assertThat().body("password", is(emptyOrNullString()))
                .assertThat().body("hiringDate", equalTo(DateProcessor.toString(person.getHiringDate())))
                .assertThat().body("id", not(emptyOrNullString()));
    }

    private Person buildPerson(final String username, final String firstName, final String lastName, final String password) {
        Person person = new Person();
        person.setUsername(username);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setPassword(password);
        person.setHiringDate(LocalDateTime.now());
        return person;
    }
}