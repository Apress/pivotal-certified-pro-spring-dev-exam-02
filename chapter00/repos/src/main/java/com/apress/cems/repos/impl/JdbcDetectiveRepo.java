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

import com.apress.cems.dao.Detective;
import com.apress.cems.repos.DetectiveRepo;
import com.apress.cems.repos.util.DetectiveRowMapper;
import com.apress.cems.util.Rank;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Transactional
@Repository
public class JdbcDetectiveRepo extends JdbcAbstractRepo<Detective> implements DetectiveRepo {

    private RowMapper<Detective> rowMapper = new DetectiveRowMapper();

    public JdbcDetectiveRepo(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Optional<Detective> findById(Long id) {
        var sql = "select d.ID, d.BADGE_NUMBER, d.RANK, d.ARMED, d.STATUS,d.PERSON_ID, " +
                "p.USERNAME, p.FIRSTNAME, p.LASTNAME, p.HIRINGDATE "+
                "from DETECTIVE d, PERSON p where d.ID= ? and d.PERSON_ID=p.ID";
        return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    @Override
    public Optional<Detective> findByBadgeNumber(String badgeNumber) {
        var sql = "select ID, BADGE_NUMBER, RANK, ARMED, STATUS,PERSON_ID from DETECTIVE where BADGE_NUMBER= ?";
        var detective = jdbcTemplate.queryForObject(sql, rowMapper, badgeNumber);
        return detective == null ? Optional.empty() : Optional.of(detective);
    }

    @Override
    public void save(Detective detective) {
        jdbcTemplate.update(
                "insert into DETECTIVE(ID, BADGE_NUMBER, RANK, ARMED, STATUS,PERSON_ID) values(?,?,?,?,?,?)",
                detective.getId(), detective.getBadgeNumber(), detective.getRank(), detective.getStatus(), detective.getPerson().getId()
        );
    }

    @Override
    public Set<Detective> findAll() {
        var sql = "select ID, BADGE_NUMBER, RANK, ARMED, STATUS,PERSON_ID from DETECTIVE";
        return new HashSet<>(jdbcTemplate.query(sql, rowMapper));
    }

    @Override
    public Set<Detective> findbyRank(Rank rank) {
        var sql = "select ID, BADGE_NUMBER, RANK, ARMED, STATUS,PERSON_ID from DETECTIVE where RANK= ?";
        return new HashSet<>(jdbcTemplate.query(sql, rowMapper, rank));
    }

    @Override
    public void delete(Detective entity) {
        jdbcTemplate.update("delete from DETECTIVE where ID =? ", entity.getId());
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("delete from DETECTIVE where ID =? ", id);
    }
}
