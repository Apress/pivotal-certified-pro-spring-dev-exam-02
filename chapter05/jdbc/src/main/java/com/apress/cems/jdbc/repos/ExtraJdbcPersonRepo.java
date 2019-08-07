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
import com.apress.cems.repos.impl.JdbcPersonRepo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Repository("extraJdbcPersonRepo")
public class ExtraJdbcPersonRepo extends JdbcPersonRepo {

    public ExtraJdbcPersonRepo(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public int createPerson(Long entityId, String username, String firstName, String lastName, String password) {
        return jdbcTemplate.update(
                "insert into PERSON(ID, USERNAME, FIRSTNAME, LASTNAME, PASSWORD, HIRINGDATE, CREATED_AT, MODIFIED_AT, VERSION) " +
                        "values(?, ?, ?, ?, ?, ?, ?, ?, ? )",
                entityId, username,firstName,lastName,password, LocalDateTime.now(),LocalDateTime.now(),LocalDateTime.now(), 1L);
    }

    @Override
    public Set<Person> findAllByUsernamePart(String part) {
        String sql = "select ID, USERNAME, FIRSTNAME, LASTNAME, PASSWORD, HIRINGDATE from PERSON where USERNAME like '%' || ? || '%' ";
        return new HashSet<Person>(jdbcTemplate.query(sql, new Object[]{part}, rowMapper));
    }

    @Override
    public Map<String, Object> findByIdAsMap(Long id) {
        String sql = "select * from PERSON where ID= ?";
        return jdbcTemplate.queryForMap(sql, id);
    }

    @Override
    public List<Map<String, Object>> findAllAsMaps() {
        String sql = "select * from PERSON";
        return jdbcTemplate.queryForList(sql);
    }

    public void htmlAllByName(String name) {
        String sql = "select * from PERSON where USERNAME= ?";
        jdbcTemplate.query(sql, new HTMLPersonRowCallbackHandler(System.out), name);
    }

    private class HTMLPersonRowCallbackHandler implements RowCallbackHandler {

        private PrintStream out;

        HTMLPersonRowCallbackHandler(PrintStream out) {
            this.out = out;
        }

        @Override
        public void processRow(ResultSet rs) throws SQLException {
            out.print("<p>person ID: ".concat(rs.getLong("ID") + "").concat("</p></br>\n")
                    .concat("<p>username: ").concat(rs.getString("USERNAME")).concat("</p></br>\n")
                    .concat("<p>firstname: ").concat(rs.getString("FIRSTNAME")).concat("</p></br>\n")
                    .concat("<p>lastname: ").concat(rs.getString("LASTNAME")).concat("</p></br>\n"));
        }
    }

}
