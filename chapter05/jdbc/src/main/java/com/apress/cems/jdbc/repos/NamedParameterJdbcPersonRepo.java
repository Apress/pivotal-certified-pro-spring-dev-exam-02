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
package com.apress.cems.jdbc.repos;

import com.apress.cems.dao.Person;
import com.apress.cems.repos.PersonRepo;
import com.apress.cems.repos.util.PersonRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Repository("jdbcNamedPersonRepo")
public class NamedParameterJdbcPersonRepo implements PersonRepo {

    private RowMapper<Person> rowMapper = new PersonRowMapper();

    private NamedParameterJdbcTemplate jdbcNamedTemplate;

    public NamedParameterJdbcPersonRepo(NamedParameterJdbcTemplate jdbcNamedTemplate) {
        this.jdbcNamedTemplate = jdbcNamedTemplate;
    }

    @Override
    public Optional<Person> findById(Long entityId) {
        String sql = "select ID, USERNAME, FIRSTNAME, LASTNAME, PASSWORD, HIRINGDATE from PERSON where ID= :id";
        return Optional.of(jdbcNamedTemplate.queryForObject(sql, Map.of("id", entityId) ,rowMapper));
    }

    @Override
    public int createPerson(Long entityId, String username, String firstName, String lastName, String password) {
        Map<String, Object> params = Map.of(
                "id", entityId,
                "un", username,
                "fn", firstName,
                "ln", lastName,
                "password", password,
                "hd", LocalDateTime.now(),
                "createdAt", LocalDateTime.now(),
                "modifiedAt", LocalDateTime.now(),
                "version", 1
        );
        return jdbcNamedTemplate.update(
                "insert into PERSON(ID, USERNAME, FIRSTNAME, LASTNAME, PASSWORD, HIRINGDATE, CREATED_AT, MODIFIED_AT, VERSION) " +
                        "values(:id, :un, :fn, :ln, :password, :hd, :createdAt, :modifiedAt, :version )",
                params);
    }

    @Override
    public int updatePassword(Long personId, String newPass) {
        String sql = "update PERSON set password= :newpass where ID= :id";
        return jdbcNamedTemplate.update(sql, Map.of("id", personId, "newpass", newPass));
    }

    @Override
    public Optional<Person> findByUsername(String username) {
        String sql = "select ID, USERNAME, FIRSTNAME, LASTNAME, PASSWORD, HIRINGDATE from PERSON where USERNAME= :un";
        return Optional.of(jdbcNamedTemplate.queryForObject(sql, Map.of("un", username) ,rowMapper));
    }

    @Override
    public Optional<Person> findByCompleteName(String firstName, String lastName) {
        String sql = "select ID, USERNAME, FIRSTNAME, LASTNAME, PASSWORD, HIRINGDATE from PERSON where FIRSTNAME= :fn and LASTNAME= :ln";
        return Optional.of(jdbcNamedTemplate.queryForObject(sql, Map.of("fn", firstName, "ln", lastName) ,rowMapper));
    }

    @Override
    public Set<Person> findAll() {
        String sql = "select * from PERSON";
        return new HashSet<>(jdbcNamedTemplate.query(sql, rowMapper));
    }

    @Override
    public long count() {
        String sql = "select count(*) from PERSON";
        return jdbcNamedTemplate.queryForObject(sql, new HashMap<>(), Integer.class);
    }

    @Override
    public void save(Person person) {
        Map<String, Object> params = Map.of(
                "id", person.getId(),
                "un", person.getUsername(),
                "fn", person.getFirstName(),
                "ln", person.getLastName(),
                "password", person.getPassword(),
                "hd", LocalDateTime.now(),
                "createdAt", LocalDateTime.now(),
                "modifiedAt", LocalDateTime.now(),
                "version", 1
        );
        jdbcNamedTemplate.update(
                "insert into PERSON(ID, USERNAME, FIRSTNAME, LASTNAME, PASSWORD, HIRINGDATE, CREATED_AT, MODIFIED_AT, VERSION) " +
                        "values(:id, :un, :fn, :ln, :password, :hd, :createdAt, :modifiedAt, :version )",
                params);
    }

    @Override
    public void delete(Person entity) {
        jdbcNamedTemplate.update("delete from p_user where id = :id", Map.of("id", entity.getId()));
    }

    @Override
    public Person update(Person entity) {
        return null;
    }

    @Override
    public int deleteById(Long entityId) {
        return jdbcNamedTemplate.update("delete from p_user where id = :id", Map.of("id", entityId));
    }
}
