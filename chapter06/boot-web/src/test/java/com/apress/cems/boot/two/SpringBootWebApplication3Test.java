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
package com.apress.cems.boot.two;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootWebApplication3Test {

    @LocalServerPort
    private Integer port;

    @Test
    void testListPersons() throws Exception {
              String responseStr =   given().baseUri("http://localhost")
                .port(port).when().get("/persons/list")
                .then()
                .assertThat().statusCode(HttpStatus.OK.value())
                .extract().body().asString();

              assertAll(
                      () -> assertTrue(responseStr.contains("div class=\"persons\"")),
                      () -> assertTrue(responseStr.contains("sherlock.holmes")),
                      () -> assertTrue(responseStr.contains("nancy.drew"))
              );
    }

    @Test
    void testShowPerson() throws Exception {
        String responseStr = given().baseUri("http://localhost")
                .port(port).when().get("/persons/1")
                .then()
                .assertThat().statusCode(HttpStatus.OK.value())
                .extract().body().asString();

        assertAll(
                () -> assertTrue(responseStr.contains("sherlock.holmes")),
                () -> assertTrue(responseStr.contains("Employed since"))
        );
    }

    @Test
    void testErrorPerson() throws Exception {
        String responseStr = given().baseUri("http://localhost")
                .port(port).when().get("/persons/99")
                .then()
                .assertThat().statusCode(HttpStatus.NOT_FOUND.value())
                .extract().body().asString();

        assertAll(
                () -> assertTrue(responseStr.contains("Unexpected error")),
                () -> assertTrue(responseStr.contains("Person with id: 99 does not exist!"))
        );
    }
}
