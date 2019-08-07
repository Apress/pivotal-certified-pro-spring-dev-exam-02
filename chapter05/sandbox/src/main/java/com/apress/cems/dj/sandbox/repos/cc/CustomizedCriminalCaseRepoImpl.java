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
package com.apress.cems.dj.sandbox.repos.cc;

import com.apress.cems.dao.CriminalCase;
import com.apress.cems.dao.Detective;
import com.apress.cems.dao.Person;
import com.apress.cems.util.EmploymentStatus;
import com.apress.cems.util.Rank;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public class CustomizedCriminalCaseRepoImpl implements CustomizedCriminalCaseRepo {

    private JdbcTemplate jdbcTemplate;

    public CustomizedCriminalCaseRepoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Detective> getTeam(CriminalCase criminalCase) {
        List<Detective> detectiveList = new ArrayList<>();
        detectiveList.add(criminalCase.getLeadInvestigator());
        List<Detective> team = jdbcTemplate.query("select d.ID, d.BADGENUMBER, d.RANK, d.ARMED, d.STATUS,d.PERSON_ID, " +
                " p.USERNAME, p.FIRSTNAME, p.LASTNAME, p.HIRINGDATE " +
                " from DETECTIVE d, PERSON p, WORKING_DETECTIVE_CASE wdc" +
                " where d.PERSON_ID=p.ID" +
                " and d.ID = wdc.DETECTIVE_ID " +
                " and wdc.CASE_ID =?", rowMapper, criminalCase.getId());
        if (team != null && !team.isEmpty()) {
            detectiveList.addAll(team);
        }
        return detectiveList;
    }

    private RowMapper<Detective> rowMapper = (rs, i) -> {
        Long id = rs.getLong("ID");
        String badgeNumber = rs.getString("BADGENUMBER");
        String rank = rs.getString("RANK");
        Boolean armed = rs.getBoolean("ARMED");
        String status = rs.getString("STATUS");
        Long personId = rs.getLong("PERSON_ID");

        Person person = new Person();
        person.setId(personId);
        person.setUsername(rs.getString("USERNAME"));
        person.setFirstName(rs.getString("FIRSTNAME"));
        person.setLastName(rs.getString("LASTNAME"));
        person.setHiringDate(rs.getTimestamp("HIRINGDATE").toLocalDateTime());

        Detective detective = new Detective();
        detective.setId(id);
        detective.setPerson(person);
        detective.setBadgeNumber(badgeNumber);
        detective.setRank(Rank.valueOf(rank));
        detective.setArmed(armed);
        detective.setStatus(EmploymentStatus.valueOf(status));

        return detective;
    };

}