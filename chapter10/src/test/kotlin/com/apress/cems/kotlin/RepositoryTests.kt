package com.apress.cems.kotlin

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@DataJpaTest
class RepositoryTests @Autowired constructor(
        val entityManager: TestEntityManager,
        val personRepo: PersonRepo) {

    @Test
    fun `Person save`(){
        val person  =  Person("test", "person", "test.person","test", DateProcessor.toDate("1983-08-15 00:23"))
        val savedPerson = personRepo.save(person)

        assertThat(savedPerson.id).isNotEqualTo(0)
        assertThat(savedPerson).isEqualTo(person)
    }

    @Test
    fun `Person findByCompleteName`() {
        val person = Person("test", "person", "test.person", "test", DateProcessor.toDate("1983-08-15 00:23"))
        entityManager.persist(person)

        val found  = personRepo.findByCompleteName("test", "person");
        assertThat(found.get()).isEqualTo(person)
    }
}