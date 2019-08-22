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
package com.apress.cems.pojos.services.impl;

import com.apress.cems.dao.*;
import com.apress.cems.pojos.repos.CriminalCaseRepo;
import com.apress.cems.pojos.repos.DetectiveRepo;
import com.apress.cems.pojos.repos.EvidenceRepo;
import com.apress.cems.pojos.repos.StorageRepo;
import com.apress.cems.pojos.services.OperationsService;
import com.apress.cems.pojos.services.ServiceException;
import com.apress.cems.util.CaseStatus;
import com.apress.cems.util.CaseType;
import com.apress.cems.util.NumberGenerator;
import com.apress.cems.util.Rank;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public class SimpleOperationsService implements OperationsService {
    private CriminalCaseRepo criminalCaseRepo;
    private EvidenceRepo evidenceRepo;
    private DetectiveRepo detectiveRepo;
    private StorageRepo storageRepo;

    @Override
    public Detective createDetective(String firstName, String lastName, LocalDateTime hiringDate, Rank rank) {
        var person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setHiringDate(hiringDate);
        person.setPassword(NumberGenerator.getPassword());
        Detective detective = new Detective();
        detective.setPerson(person);
        detective.setRank(rank);
        detective.setBadgeNumber(NumberGenerator.getBadgeNumber());
        detectiveRepo.save(detective);
        return detective;
    }

    @Override
    public CriminalCase createCriminalCase(CaseType caseType, String shortDescription, String badgeNo, Map<Evidence, String> evidenceMap) {
        var detectiveOpt = detectiveRepo.findByBadgeNumber(badgeNo);
        var criminalCase = new CriminalCase();
        criminalCase.setType(caseType);
        criminalCase.setShortDescription(shortDescription);
        detectiveOpt.ifPresent(criminalCase::setLeadInvestigator);
        criminalCaseRepo.save(criminalCase);

        evidenceMap.forEach((ev, storageName) -> {
            var storageOpt = storageRepo.findByName(storageName);
            if (storageOpt.isPresent()) {
                ev.setStorage(storageOpt.get());
                criminalCase.addEvidence(ev);
                evidenceRepo.save(ev);
            } else {
                throw new ServiceException("Evidence Storage not present in the system");
            }
        });
        return criminalCase;
    }

    @Override
    public Optional<CriminalCase> assignLeadInvestigator(String caseNumber, String leadDetectiveBadgeNo) {
        var opt = criminalCaseRepo.findByNumber(caseNumber);
        var detectiveOpt = detectiveRepo.findByBadgeNumber(leadDetectiveBadgeNo);
        if (opt.isPresent()) {
            var criminalCase = opt.get();
            if (detectiveOpt.isPresent()) {
                criminalCase.setLeadInvestigator(detectiveOpt.get());
                criminalCaseRepo.save(criminalCase);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<CriminalCase> linkEvidence(String caseNumber, List<Evidence> evidenceList) {
        var opt = criminalCaseRepo.findByNumber(caseNumber);
        if (opt.isPresent()) {
            var criminalCase = opt.get();
            criminalCase.getEvidenceSet().forEach(evidence -> {
                evidence.setCriminalCase(criminalCase);
                evidenceRepo.save(evidence);
            });
            return opt;
        }
        return Optional.empty();
    }

    @Override
    public boolean solveCase(String caseNumber, String reason) {
        criminalCaseRepo.findByNumber(caseNumber).ifPresent(criminalCase -> {
            criminalCase.setStatus(CaseStatus.CLOSED);
            criminalCase.getEvidenceSet().forEach(evidence -> {
                evidence.setArchived(true);
            });
            criminalCaseRepo.save(criminalCase);
        });
        return true;
    }

    @Override
    public Set<Detective> getAssignedTeam(String caseNumber) {
        var opt = criminalCaseRepo.findByNumber(caseNumber);
        if (opt.isPresent()) {
            return opt.get().getAssigned();
        }
        return new HashSet<>();
    }

    //setters
    @Override
    public void setCriminalCaseRepo(CriminalCaseRepo criminalCaseRepo) {
        this.criminalCaseRepo = criminalCaseRepo;
    }

    @Override
    public void setEvidenceRepo(EvidenceRepo evidenceRepo) {
        this.evidenceRepo = evidenceRepo;
    }

    @Override
    public void setDetectiveRepo(DetectiveRepo detectiveRepo) {
        this.detectiveRepo = detectiveRepo;
    }

    @Override
    public void setStorageRepo(StorageRepo storageRepo) {
        this.storageRepo = storageRepo;
    }
}
