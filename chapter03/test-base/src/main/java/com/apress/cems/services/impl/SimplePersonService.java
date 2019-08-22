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
package com.apress.cems.services.impl;

import com.apress.cems.dao.Person;
import com.apress.cems.repos.AbstractRepo;
import com.apress.cems.repos.PersonRepo;
import com.apress.cems.services.PersonService;

import java.util.Optional;
import java.util.Set;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public class SimplePersonService extends SimpleAbstractService<Person> implements PersonService {
    private PersonRepo repo;

    @Override
    public Person createPerson(String firstName, String lastName) {
        var person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        repo.save(person);
        return person;
    }

    @Override
    public Optional<Person> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public Optional<Person> findByFirstNameAndLastName(String firstName, String lastName) {
        return repo.findByCompleteName(firstName, lastName);
    }

    public void setRepo(PersonRepo repo) {
        this.repo = repo;
    }

    @Override
    AbstractRepo<Person> getRepo() {
        return repo;
    }
}
