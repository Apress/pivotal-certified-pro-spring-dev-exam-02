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
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Repository
public class JdbcDetectiveRepo extends JdbcAbstractRepo<Detective> implements DetectiveRepo {

    private RowMapper<Detective> rowMapper = new DetectiveRowMapper();

    @Autowired
    public JdbcDetectiveRepo(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Optional<Detective> findById(Long id) {
        String sql = "select id, badgenumber, rank, armed, employmentstatus, person_id from detective where id= ?";
        return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    @Override
    public Optional<Detective> findByBadgeNumber(String badgeNumber) {
        String sql = "select id, badgenumber, rank, armed, employmentstatus, person_id from detective where badgenumber= ?";
        Detective detective = jdbcTemplate.queryForObject(sql, rowMapper, badgeNumber);
        return detective == null ? Optional.empty() : Optional.of(detective);
    }

    @Override
    public void save(Detective detective) {
        jdbcTemplate.update(
                "insert into detective(id, badgenumber, rank, armed, employmentstatus, person_id ) values(?,?,?,?,?,?)",
                detective.getId(), detective.getBadgeNumber(), detective.getRank(), detective.getStatus(), detective.getPerson().getId()
        );
    }

    @Override
    public Set<Detective> findbyRank(Rank rank) {
        throw new NotImplementedException("Not needed for this implementation.");
    }

    @Override
    public void delete(Detective entity) {
        jdbcTemplate.update("delete from criminalcase where id =? ", entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("delete from criminalcase where id =? ", id);
    }
}
