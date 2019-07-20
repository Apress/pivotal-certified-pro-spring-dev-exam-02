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
package com.apress.cems.web.controllers;

import com.apress.cems.dao.Person;
import com.apress.cems.dj.services.PersonService;
import com.apress.cems.web.problem.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    @Mock //Creates mock instance of the field it annotates
    private PersonService mockService;

    @InjectMocks
    private PersonController personController;

    @SuppressWarnings("unchecked")
    @Test
    void testListHandler() {
        List<Person> list = new ArrayList<>();
        Person p = new Person();
        p.setId(1L);
        list.add(p);

        when(mockService.findAll()).thenReturn(list);

        ExtendedModelMap model = new ExtendedModelMap();
        String viewName = personController.list(model);
        List<Person> persons = (List<Person>) model.get("persons");

        assertAll(
                () -> assertNotNull(persons),
                () -> assertEquals(1, persons.size()),
                () -> assertEquals("persons/list", viewName)
        );
    }

    @Test
    void testShowHandler() throws NotFoundException {
        Person p = new Person();
        p.setId(1L);

        when(mockService.findById(any(Long.class))).thenReturn(Optional.of(p));

        ExtendedModelMap model = new ExtendedModelMap();
        String viewName = personController.show(1L, model);
        Person person = (Person) model.get("person");

        assertAll(
                () -> assertNotNull(person),
                () -> assertEquals(1L, person.getId()),
                () -> assertEquals("persons/show", viewName)
        );
    }
}