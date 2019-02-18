package com.apress.cems.pojos.services;

import com.apress.cems.dao.CriminalCase;
import com.apress.cems.dao.Detective;
import com.apress.cems.dao.Evidence;
import com.apress.cems.pojos.repos.CriminalCaseRepo;
import com.apress.cems.pojos.repos.DetectiveRepo;
import com.apress.cems.pojos.repos.EvidenceRepo;
import com.apress.cems.pojos.repos.StorageRepo;
import com.apress.cems.util.CaseType;
import com.apress.cems.util.Rank;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface OperationsService {

    Detective createDetective(String firstName, String lastName, LocalDateTime hiringDate, Rank rank);

    CriminalCase createCriminalCase(CaseType caseType, String shortDescription, String badgeNo, Map<Evidence, String> evidenceAndLocations);

    Optional<CriminalCase> assignLeadInvestigator(String caseNumber, String leadDetectiveBadgeNo);

    Optional<CriminalCase> linkEvidence(String caseNumber, List<Evidence> evidenceList);

    boolean solveCase(String caseNumber, String reason);

    Set<Detective> getAssignedTeam(String caseNumber);

    // setter skeletons for setting repositories

    void setCriminalCaseRepo(CriminalCaseRepo criminalCaseRepo);

    void setEvidenceRepo(EvidenceRepo evidenceRepo);

    void setDetectiveRepo(DetectiveRepo detectiveRepo);

    void setStorageRepo(StorageRepo storageRepo);

}
