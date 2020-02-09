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
package com.apress.cems.hib.services;

import com.apress.cems.aop.exception.MailSendingException;
import com.apress.cems.aop.service.PersonService;
import com.apress.cems.dao.Person;
import com.apress.cems.repos.PersonRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Service("personServiceImpl")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class PersonServiceImpl implements PersonService {
    private PersonRepo personRepo;

    public PersonServiceImpl(@Qualifier("hibernatePersonRepo") PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    public Set<Person> findAll() {
        return Set.copyOf(personRepo.findAll());
    }

    @Override
    public long countPersons() {
        return personRepo.count();
    }

    @Override
    public Optional<Person> findById(Long id) {
        return personRepo.findById(id);
    }

    @Transactional
    @Override
    public Person save(Person person) {
        personRepo.save(person);
        return person;
    }

    @Transactional
    @Override
    public Person updateFirstName(Person person, String newFirstname) {
        return personRepo.update(person);
    }

    @Transactional
    @Override
    public Person updatePassword(Person person, String password) throws MailSendingException {
        person.setPassword(password);
        Person p =  personRepo.update(person);
        sendNotification();
        return p;
    }

    private void sendNotification() throws MailSendingException {
        if (true) {
            throw new MailSendingException("Confirmation email for password could not be sent. Password was not updated.");
        }
    }

    @Override
    public Optional<Person> findByUsername(String username) {
        return personRepo.findByUsername(username);
    }

    @Transactional(propagation = Propagation.NESTED, readOnly = true)
    @Override
    public String getPersonAsHtml(String username) {
        final StringBuilder sb = new StringBuilder();
        personRepo.findByUsername(username).ifPresentOrElse(
                p -> sb.append("<p>First Name: ").append(p.getFirstName()).append(" </p>")
                        .append("<p>Last Name: ").append(p.getLastName()).append(" </p>"),
                () -> sb.append("<p>None found with username ").append(username).append(" </p>")
        );
        return sb.toString();
    }

    @Override
    public Optional<Person> findByCompleteName(String firstName, String lastName) {
        return personRepo.findByCompleteName(firstName,lastName);
    }

    @Transactional
    @Override
    public void delete(Person person) {
        personRepo.delete(person);
    }
}
