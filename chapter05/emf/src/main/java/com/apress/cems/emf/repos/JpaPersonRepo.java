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
package com.apress.cems.emf.repos;

import com.apress.cems.dao.Person;
import com.apress.cems.repos.PersonRepo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@SuppressWarnings("unchecked")
@Repository("jpaPersonRepo")
public class JpaPersonRepo implements PersonRepo {

    private EntityManager entityManager;

    @PersistenceContext
    void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Set<Person> findAll() {
        List persons = (List<Person>) entityManager.createQuery("select p from Person p").getResultList();
        return persons.isEmpty()? Set.of() : new HashSet<>(persons);
    }

    @Override
    public List<Person> findAllByLastName(String lastName) {
        //create the query
        CriteriaBuilder builder= entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> query = builder.createQuery(Person.class);
        Root<Person> personRoot = query.from(Person.class);
        ParameterExpression<String> value = builder.parameter(String.class);
        query.select(personRoot).where(builder.equal(personRoot.get("lastName"), value));

        // execute the query
        TypedQuery<Person> tquery = entityManager.createQuery(query);
        tquery.setParameter(value,lastName);
        return tquery.getResultList();

       /* return  (List<Person>) entityManager
                .createQuery(Person.FIND_BY_LAST_NAME)
                .setParameter(1, lastName).getResultList();*/
    }

    @Override
    public List<String> findAllUsernames() {
        Query nq = entityManager
                .createNativeQuery("select USERNAME from PERSON");
        return (List<String>) nq.getResultList();
    }

    @Override
    public Optional<Person> findById(Long entityId) {
        Person person = entityManager.find(Person.class, entityId);
        return person == null? Optional.empty() :Optional.of(person);
    }

    @Override
    public Optional<Person> findByUsername(String username) {
        Person person = (Person) entityManager
                .createQuery("from Person p where p.username= ?1")
                .setParameter(1, username).getSingleResult();
        return person == null? Optional.empty() :Optional.of(person);
    }

    @Override
    public Optional<Person> findByCompleteName(String firstName, String lastName) {
        /*
        Person person = (Person) entityManager
                .createQuery("from Person p where p.firstName=?1 and p.lastName=?2")
                .setParameter(1, firstName)
                .setParameter(2, lastName)
                .getSingleResult();
        */
        Person person = (Person) entityManager
                .createNamedQuery(Person.FIND_BY_COMPLETE_NAME)
                .setParameter("fn", firstName)
                .setParameter("ln", lastName)
                .getSingleResult();
        return person == null? Optional.empty() :Optional.of(person);
    }

    @Override
    public int updatePassword(Long personId, String newPass) {
        Person person = entityManager.find(Person.class, personId);
        if(person != null) {
            person.setPassword(newPass);
            entityManager.merge(person);
        }
        return 0;
    }

    @Override
    public long count() {
        return (Long) entityManager.createQuery("select count(p) from Person p").getSingleResult();
    }

    @Override
    public void save(Person person) {
        entityManager.persist(person);
    }

    @Override
    public void delete(Person person) {
        entityManager.remove(person);
    }

    @Override
    public Person update(Person person) {
        entityManager.merge(person);
        return person;
    }

    @Override
    public int deleteById(Long entityId) {
        Person person = entityManager.find(Person.class, entityId);
        entityManager.remove(person);
        return 1;
    }
}
