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
package com.apress.cems.tx.services;

import com.apress.cems.aop.service.DetectiveService;
import com.apress.cems.dao.Detective;
import com.apress.cems.dao.Person;
import com.apress.cems.repos.DetectiveRepo;
import com.apress.cems.util.EmploymentStatus;
import com.apress.cems.util.NumberGenerator;
import com.apress.cems.util.Rank;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;
import java.util.Set;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Service("programaticDetectiveService")
public class ProgramaticDetectiveService implements DetectiveService {
    private DetectiveRepo detectiveRepo;
    private TransactionTemplate txTemplate;

    public ProgramaticDetectiveService(DetectiveRepo detectiveRepo, PlatformTransactionManager transactionManager) {
        this.detectiveRepo = detectiveRepo;
        this.txTemplate = new TransactionTemplate(transactionManager);
    }

    @Override
    public Optional<Detective> findById(Long id) {
        return txTemplate.execute(status -> {
            Optional<Detective> opt = null;
            try {
                opt = detectiveRepo.findById(id);
            } catch (Exception e) {
                status.setRollbackOnly();
            }
            return opt;
        });
    }

    @Override
    public Set<Detective> findAll() {
        return txTemplate.execute(status ->  detectiveRepo.findAll());
    }

    @Override
    public Detective createDetective(Person person, Rank rank) {
        Detective detective = new Detective();
        detective.setPerson(person);
        detective.setRank(rank);
        detective.setBadgeNumber(NumberGenerator.getBadgeNumber());
        detective.setArmed(false);
        detective.setStatus(EmploymentStatus.ACTIVE);

        return txTemplate.execute(status -> {
            try {
                detectiveRepo.save(detective);
            } catch (Exception e) {
                status.setRollbackOnly();
            }
            return detective;
        });
    }

    @Override
    public Optional<Detective> findByBadgeNumber(String badgeNumber) {
        return txTemplate.execute(status -> detectiveRepo.findByBadgeNumber(badgeNumber));
    }

    @Override
    public Set<Detective> findByRank(Rank rank) {
        return txTemplate.execute(status -> detectiveRepo.findbyRank(rank));
    }
}
