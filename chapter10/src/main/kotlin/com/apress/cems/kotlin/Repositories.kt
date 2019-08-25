package com.apress.cems.kotlin

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime
import java.util.*

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
interface PersonRepo : JpaRepository<Person, Long> {
    @Query("select p from Person p where p.username like %?1%")
    fun findByUsername(username: String): Optional<Person>

    @Query("select p from Person p where p.firstName=:fn and p.lastName=:ln")
    fun findByCompleteName(@Param("fn") fn: String, @Param("ln") lastName: String): Optional<Person>

    @Query("select p from Person p where p.username like %?1%")
    fun findByUsernameLike(username: String): List<Person>

    @Query("select p from Person p where p.firstName=:fn")
    fun findByFirstName(@Param("fn") firstName: String): List<Person>

    @Query("select p from Person p where p.firstName like %?1%")
    fun findByFirstNameLike(firstName: String): List<Person>

    @Query("select p from Person p where p.lastName=:ln")
    fun findByLastName(@Param("ln") lastName: String): List<Person>

    @Query("select p from Person p where p.lastName like %?1%")
    fun findByLastNameLike(lastName: String): List<Person>

    @Query("select p from Person p where p.hiringDate=:hd")
    fun findByHiringDate(@Param("hd") date: LocalDateTime): List<Person>
}

interface DetectiveRepo : JpaRepository<Detective, Long>

interface CriminalCaseRepo : JpaRepository<CriminalCase, Long>