package com.apress.cems.services;

import com.apress.cems.dao.CriminalCase;
import com.apress.cems.dao.Detective;
import com.apress.cems.dao.Evidence;
import com.apress.cems.repos.CriminalCaseRepo;
import com.apress.cems.repos.DetectiveRepo;
import com.apress.cems.repos.StorageRepo;
import com.apress.cems.util.CaseType;
import com.apress.cems.util.Rank;
import com.apress.cems.repos.EvidenceRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
