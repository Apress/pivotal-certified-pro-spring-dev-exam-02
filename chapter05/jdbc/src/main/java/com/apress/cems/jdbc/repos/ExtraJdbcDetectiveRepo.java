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

import com.apress.cems.dao.Detective;
import com.apress.cems.dao.Person;
import com.apress.cems.repos.impl.JdbcDetectiveRepo;
import com.apress.cems.util.EmploymentStatus;
import com.apress.cems.util.Rank;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Repository
public class ExtraJdbcDetectiveRepo extends JdbcDetectiveRepo {

    public ExtraJdbcDetectiveRepo(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Optional<Detective> findByIdWithPersonDetails(Long id) {
        String sql = "select d.ID id," +
                " p.ID pid, " +
                " p.USERNAME un," +
                " p.FIRSTNAME fn, " +
                " p.LASTNAME ln, " +
                " p.HIRINGDATE hd," +
                " d.BADGE_NUMBER bno," +
                " d.RANK rank," +
                " d.ARMED armed," +
                " d.STATUS status" +
                " from DETECTIVE d, PERSON p where d.PERSON_ID=p.ID and d.ID=" + id;
        return Optional.of(jdbcTemplate.query(sql, new DetectiveExtractor()));
    }

    private class DetectiveExtractor implements ResultSetExtractor<Detective> {
        @Override
        public Detective extractData(ResultSet rs) throws SQLException {
            Detective detective = null;
            while (rs.next()) {
                if (detective == null) {
                    detective = new Detective();
                    // set internal entity identifier (primary key)
                    detective.setId(rs.getLong("id"));
                    detective.setBadgeNumber(rs.getString("bno"));
                    detective.setRank(Rank.valueOf(rs.getString("rank")));
                    detective.setArmed(rs.getBoolean("armed"));
                    detective.setStatus(EmploymentStatus.valueOf(rs.getString("status")));
                }
                Person p = new Person();
                p.setId(rs.getLong("pid"));
                p.setUsername(rs.getString("un"));
                p.setFirstName(rs.getString("fn"));
                p.setLastName(rs.getString("ln"));
                p.setHiringDate(rs.getTimestamp("hd").toLocalDateTime());
                detective.setPerson(p);
            }
            return detective;
        }
    }
}
