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
package com.apress.cems.boot;

import com.apress.cems.person.Person;
import com.apress.cems.person.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@WebMvcTest
class SpringBootWebApplicationTest {

    private MockMvc mockMvc;

    @MockBean
    private PersonService mockService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testList() throws Exception {
        List<Person> list = new ArrayList<>();
        Person p = new Person();
        p.setId(1L);
        p.setFirstName("Sherlock");
        p.setLastName("Holmes");
        list.add(p);
        when(mockService.findAll()).thenReturn(list);

        mockMvc.perform(get("/persons/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("persons/list"))
                .andExpect(model().attribute("persons", hasSize(1)))
                .andExpect(model().attribute("persons", hasItem(
                        anyOf(
                                hasProperty("id", is(1L)),
                                hasProperty("firstName", is("Sherlock")),
                                hasProperty("lastName", is("Holmes"))
                        )
                )));

    }

    @Test
    void testShowPerson() throws Exception {
        Person p = new Person();
        p.setId(1L);
        p.setFirstName("Sherlock");
        p.setLastName("Holmes");

        when(mockService.findById(anyLong())).thenReturn(Optional.of(p));

        mockMvc.perform(get("/persons/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("persons/show"))
                .andExpect(model().attribute("person", hasProperty("id", is(1L))))
                .andExpect(model().attribute("person", hasProperty("firstName", is("Sherlock"))))
                .andExpect(model().attribute("person", hasProperty("lastName", is("Holmes"))));

    }
}
