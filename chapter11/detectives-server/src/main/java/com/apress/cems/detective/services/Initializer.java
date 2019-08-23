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
package com.apress.cems.detective.services;

import com.apress.cems.detective.Detective;
import com.apress.cems.util.EmploymentStatus;
import com.apress.cems.util.NumberGenerator;
import com.apress.cems.util.Rank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Service
@Transactional
public class Initializer {
    private Logger logger = LoggerFactory.getLogger(Initializer.class);

    private DetectiveService detectiveService;


    public Initializer(DetectiveService detectiveService) {
        this.detectiveService = detectiveService;
    }

    @PostConstruct
    public void init() {
        logger.info(" -->> Starting database initialization...");
        if (detectiveService.findAll().isEmpty()) {
            createDetectives();
        }
        logger.info(" -->> Database initialization finished.");
    }

    private void createDetectives() {
        Detective detective = createDetective(1L, Rank.INSPECTOR, false, EmploymentStatus.ACTIVE);
        detectiveService.save(detective);

        detective = createDetective(2L, Rank.SENIOR, true, EmploymentStatus.ACTIVE);
        detectiveService.save(detective);

        detective = createDetective(3L, Rank.TRAINEE, false, EmploymentStatus.VACATION);
        detectiveService.save(detective);

        detective = createDetective(4L, Rank.INSPECTOR, true, EmploymentStatus.SUSPENDED);
        detectiveService.save(detective);

    }

    private Detective createDetective(Long personId, Rank rank, Boolean armed, EmploymentStatus status){
        Detective detective = new Detective();
        detective.setPersonId(personId);
        detective.setBadgeNumber(NumberGenerator.getBadgeNumber());
        detective.setRank(rank);
        detective.setArmed(armed);
        detective.setStatus(status);
        return detective;
    }
}