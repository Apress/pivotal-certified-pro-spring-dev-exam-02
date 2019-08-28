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
package com.apress.cems.reactive.person;

import com.apress.cems.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface PersonRepo extends JpaRepository<Person, Long> {

    @Query("select p from Person p where p.username like %?1%")
    Optional<Person> findByUsername(String username);

    @Query("select p from Person p where p.firstName=:fn and p.lastName=:ln")
    Optional<Person> findByCompleteName(@Param("fn") String fn, @Param("ln") String lastName);

    @Query("select p from Person p where p.username like %?1%")
    List<Person> findByUsernameLike(String username);


    @Query("select p from Person p where p.firstName=:fn")
    List<Person> findByFirstName(@Param("fn") String firstName);

    @Query("select p from Person p where p.firstName like %?1%")
    List<Person> findByFirstNameLike(String firstName);

    @Query("select p from Person p where p.lastName=:ln")
    List<Person> findByLastName(@Param("ln") String lastName);

    @Query("select p from Person p where p.lastName like %?1%")
    List<Person> findByLastNameLike(String lastName);

    @Query("select p from Person p where p.hiringDate=:hd")
    List<Person> findByHiringDate(@Param("hd") LocalDateTime date);
}
