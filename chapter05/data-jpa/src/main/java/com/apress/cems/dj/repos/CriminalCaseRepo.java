package com.apress.cems.dj.repos;

import com.apress.cems.dao.CriminalCase;
import com.apress.cems.dao.Detective;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface CriminalCaseRepo extends JpaRepository<CriminalCase, Long> {
    List<CriminalCase> findByLeadInvestigator(Detective detective);
}
