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
package com.apress.cems.stub.repo;

import com.apress.cems.dao.CriminalCase;
import com.apress.cems.dao.Detective;
import com.apress.cems.repos.CriminalCaseRepo;
import com.apress.cems.util.CaseStatus;
import com.apress.cems.util.CaseType;
import com.apress.cems.util.Rank;
import org.apache.commons.lang3.NotImplementedException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.stream.Collectors;

import static com.apress.cems.stub.util.TestObjectsBuilder.buildCase;
import static com.apress.cems.stub.util.TestObjectsBuilder.buildDetective;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public class StubCriminalCaseRepo extends StubAbstractRepo<CriminalCase> implements CriminalCaseRepo {

    Map<Detective, Set<CriminalCase>> records2 = new HashMap<>();
    Detective detective;

    @PostConstruct
    public void init(){
        detective = buildDetective("Sherlock", "Holmes", Rank.INSPECTOR, "TS1234");

        // create a few entries to play with
        this.save(buildCase(detective, CaseType.FELONY, CaseStatus.UNDER_INVESTIGATION));
        this.save(buildCase(detective, CaseType.MISDEMEANOR, CaseStatus.SUBMITTED));
    }

    @Override
    public CriminalCase update(CriminalCase entity) {
        throw new NotImplementedException("Not needed for this stub.");
    }

    @Override
    public void save(CriminalCase criminalCase) {
        super.save(criminalCase);
        addWithLeadInvestigator(criminalCase);
    }

    private void addWithLeadInvestigator(CriminalCase criminalCase){
        if (criminalCase.getLeadInvestigator()!= null) {
            var lead = criminalCase.getLeadInvestigator();
            if (records2.containsKey(lead)) {
                records2.get(lead).add(criminalCase);
            } else {
                Set<CriminalCase> ccSet = new HashSet<>();
                ccSet.add(criminalCase);
                records2.put(lead, ccSet);
            }
        }
    }
    @Override
    public Set<CriminalCase> findByLeadInvestigator(Detective detective) {
        return records2.get(detective);
    }

    @Override
    public Optional<CriminalCase> findByNumber(String caseNumber) {
        final var result = new CriminalCase[1];
       records2.values().forEach(set -> set.stream()
               .filter(c -> c.getNumber().equalsIgnoreCase(caseNumber))
                   .findFirst().ifPresent(c -> result[0] = c)
       );
       return Optional.of(result[0]);
    }

    @Override
    public Set<CriminalCase> findByStatus(CaseStatus status) {
        final Set<CriminalCase> resultSet = new HashSet<>();
        records2.values().forEach(set ->
                resultSet.addAll(set.stream()
                        .filter(c -> c.getStatus().equals(status))
                        .collect(Collectors.toSet()))
        );
        return resultSet;
    }

    @Override
    public Set<CriminalCase> findByType(CaseType type) {
        throw new NotImplementedException("Not needed for this stub.");
    }

    @PreDestroy
    public void clear(){
        records2.entrySet().removeIf(e ->true);
    }
}
