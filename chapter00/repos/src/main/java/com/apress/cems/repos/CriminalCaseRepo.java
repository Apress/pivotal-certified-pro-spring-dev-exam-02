package com.apress.cems.repos;

import com.apress.cems.dao.CriminalCase;
import com.apress.cems.dao.Detective;
import com.apress.cems.util.CaseStatus;
import com.apress.cems.util.CaseType;

import java.util.Optional;
import java.util.Set;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface CriminalCaseRepo extends  AbstractRepo<CriminalCase>  {

    Set<CriminalCase> findByLeadInvestigator(Detective detective);

    Optional<CriminalCase> findByNumber(String caseNumber);

    Set<CriminalCase> findByStatus(CaseStatus status);

    Set<CriminalCase> findByType(CaseType type);

}
