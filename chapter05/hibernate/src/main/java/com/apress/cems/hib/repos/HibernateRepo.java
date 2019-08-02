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
package com.apress.cems.hib.repos;

import com.apress.cems.dao.Person;
import com.apress.cems.repos.PersonRepo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@SuppressWarnings("unchecked")
@Repository("hibernatePersonRepo")
public class HibernateRepo implements PersonRepo {

    private SessionFactory sessionFactory;

    public HibernateRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Returns the session associated with the ongoing reward transaction.
     *
     * @return the transactional session
     */
    protected Session session() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Set<Person> findAll() {
        List persons = session().createQuery("FROM Person").list();
        return persons.isEmpty()? Set.of() : new HashSet<>(persons);
    }

    @Override
    public List<Person> findAllByLastName(String lastName) {
        return  (List<Person>) session()
                .createQuery("from Person p where p.lastName= ?1")
                .setParameter(1, lastName).list();
    }

    @Override
    public List<String> findAllUsernames() {
        NativeQuery<String> nq = session()
                .createNativeQuery("select USERNAME from PERSON");
        return nq.getResultList();
    }

    @Override
    public Optional<Person> findById(Long entityId) {
        Person person = session().get(Person.class, entityId);
        return person == null? Optional.empty() :Optional.of(person);
    }

    @Override
    public Optional<Person> findByUsername(String username) {
       /* Person person = (Person) session()
                .createQuery("from Person p where p.username= ?1")
                .setParameter(1, username).uniqueResult();
        return person == null? Optional.empty() :Optional.of(person);*/

        Person person = (Person) session()
                .createQuery("from Person p where p.username= :un")
                .setParameter("un", username).uniqueResult();
        return person == null? Optional.empty() :Optional.of(person);
    }

    @Override
    public Optional<Person> findByCompleteName(String firstName, String lastName) {
        Person person = (Person) session()
                .createQuery("from Person p where p.firstName=?1 and p.lastName=?2")
                .setParameter(1, firstName)
                .setParameter(2, lastName)
                .uniqueResult();
        return person == null? Optional.empty() :Optional.of(person);
    }

    @Override
    public int updatePassword(Long personId, String newPass) {
        Person person = session().get(Person.class, personId);
        if(person != null) {
            person.setPassword(newPass);
            session().update(person);
        }
        return 0;
    }

    @Override
    public long count() {
        return (Long) session().createQuery("select count(p) from Person p").uniqueResult();
    }

    @Override
    public void save(Person person) {
        session().persist(person);
    }

    @Override
    public void delete(Person person) {
        session().delete(person);
    }

    @Override
    public Person update(Person person) {
        session().update(person);
        return person;
    }

    @Override
    public int deleteById(Long entityId) {
        Person person = session().get(Person.class, entityId);
        session().delete(person);
        return 1;
    }
}
