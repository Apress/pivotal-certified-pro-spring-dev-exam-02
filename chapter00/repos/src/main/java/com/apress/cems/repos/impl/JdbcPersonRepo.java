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
package com.apress.cems.repos.impl;

import com.apress.cems.dao.Person;
import com.apress.cems.repos.ApressRepo;
import com.apress.cems.repos.PersonRepo;
import com.apress.cems.repos.util.PersonRowMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Repository
public class JdbcPersonRepo extends JdbcAbstractRepo<Person> implements PersonRepo {
    protected RowMapper<Person> rowMapper = new PersonRowMapper();

    private static final String[] SPECIAL_CHARS = new String[]{"$", "#", "&", "%"};

    public JdbcPersonRepo(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @ApressRepo
    @Override
    public Optional<Person> findById(Long id) {
        var sql = "select ID, USERNAME, FIRSTNAME, LASTNAME, PASSWORD, HIRINGDATE from PERSON where ID= ?";
        return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    @Override
    public Optional<Person> findByUsername(String username) {
        var sql = "select ID, USERNAME, FIRSTNAME, LASTNAME, PASSWORD, HIRINGDATE from PERSON where USERNAME= ?";
        return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, username));
    }

    @Override
    public Optional<Person> findByCompleteName(String firstName, String lastName) {
        var sql = "select ID, USERNAME, FIRSTNAME, LASTNAME, PASSWORD, HIRINGDATE from PERSON where FIRSTNAME= ? and LASTNAME= ?";
        return Optional.of(jdbcTemplate.queryForObject(sql, new Object[]{firstName, lastName}, rowMapper));
    }

    @Override
    public int updatePassword(Long personId, String newPass) {
        var sql = "update PERSON set password=? where ID = ?";
        return jdbcTemplate.update(sql, newPass, personId);
    }

    @Override
    public Person update(Person person) {
        if (StringUtils.indexOfAny(person.getFirstName(), SPECIAL_CHARS) != -1 ||
                StringUtils.indexOfAny(person.getLastName(), SPECIAL_CHARS) != -1) {
            throw new IllegalArgumentException("Text contains weird characters!");
        }
        jdbcTemplate.update("update PERSON set USERNAME=?, FIRSTNAME=?, LASTNAME=?, PASSWORD=?, MODIFIED_AT=? where ID=?",
                 person.getUsername(), person.getFirstName(), person.getLastName(), person.getPassword(), LocalDateTime.now(), person.getId()
        );
        return person;
    }

    @Override
    public void save(Person person) {
        jdbcTemplate.update(
                "insert into PERSON(ID, USERNAME, FIRSTNAME, LASTNAME, PASSWORD, HIRINGDATE, MODIFIED_AT, CREATED_AT, VERSION) values(?,?,?,?,?,?,?,?,?)",
                person.getId(), person.getUsername(), person.getFirstName(), person.getLastName(), person.getPassword(),
                person.getHiringDate(), LocalDateTime.now(), LocalDateTime.now(), 1
        );
    }

    @Override
    public Set<Person> findAll() {
        var sql = "select ID, USERNAME, FIRSTNAME, LASTNAME, PASSWORD, HIRINGDATE from PERSON";
        return new HashSet<>(jdbcTemplate.query(sql, rowMapper));
    }

    @Override
    public void delete(Person entity) {
        jdbcTemplate.update("delete from PERSON where ID =? ", entity.getId());
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("delete from PERSON where ID =? ", id);
    }

    @Override
    public long count() {
        var sql = "select count(*) from PERSON";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }
}
