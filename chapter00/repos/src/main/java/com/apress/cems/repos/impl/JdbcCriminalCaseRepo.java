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

import com.apress.cems.dao.CriminalCase;
import com.apress.cems.dao.Detective;
import com.apress.cems.repos.CriminalCaseRepo;
import com.apress.cems.repos.util.CriminalCaseRowMapper;
import com.apress.cems.util.CaseStatus;
import com.apress.cems.util.CaseType;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Repository
public class JdbcCriminalCaseRepo  extends JdbcAbstractRepo<CriminalCase> implements CriminalCaseRepo {
    private RowMapper<CriminalCase> rowMapper = new CriminalCaseRowMapper();

    @Autowired
    public JdbcCriminalCaseRepo(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Optional<CriminalCase> findById(Long id) {
        String sql = "select id, case_number, case_type, status, short_description from criminalcase where id= ?";
        return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    @Override
    public Set<CriminalCase> findByLeadInvestigator(Detective detective) {
        String sql =  "select id, case_number, case_type, status, short_description from criminalcase c, detective d where c.lead_investigator=d.id and d.id= ?";
        return new HashSet<>(jdbcTemplate.query(sql, new Object[]{detective.getId()}, rowMapper));
    }

    @Override
    public Optional<CriminalCase> findByNumber(String caseNumber) {
        String sql = "select id, case_number, case_type, status, short_description from criminalcase where case_number= ?";
        CriminalCase result = jdbcTemplate.queryForObject(sql, rowMapper, caseNumber);
        return result == null ? Optional.empty() :  Optional.of(result);
    }

    @Override
    public void save(CriminalCase cc) {
        jdbcTemplate.update(
                "insert into criminalcase(id, case_number, case_type, status, short_description, lead_investigator ) values(?,?,?,?,?,?,?)",
                cc.getId(), cc.getNumber(), cc.getType(), cc.getStatus(), cc.getShortDescription(), cc.getLeadInvestigator().getId()
        );
    }

    @Override
    public void delete(CriminalCase entity) {
        jdbcTemplate.update("delete from criminalcase where id =? ", entity.getId());
    }

    @Override
    public void deleteById(Long entityId) {
        jdbcTemplate.update("delete from criminalcase where id =? ", entityId);
    }

    @Override
    public Set<CriminalCase> findByStatus(CaseStatus status) {
        throw new NotImplementedException("Not needed for this implementation.");
    }

    @Override
    public Set<CriminalCase> findByType(CaseType type) {
        throw new NotImplementedException("Not needed for this implementation.");
    }
}
