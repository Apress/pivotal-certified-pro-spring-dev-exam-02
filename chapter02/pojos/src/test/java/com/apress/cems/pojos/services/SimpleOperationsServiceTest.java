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
package com.apress.cems.pojos.services;

import com.apress.cems.dao.*;
import com.apress.cems.util.CaseType;
import com.apress.cems.util.Rank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public class SimpleOperationsServiceTest extends SimpleServiceTestBase {
    static final Long DETECTIVE_ID = 1L;
    static final String BADGE_NO = "NY112233";

    @BeforeEach
    void setUp() {
        init();
        //create detective
        var person = new Person();
        person.setId(DETECTIVE_ID);
        person.setFirstName("Sherlock");
        person.setLastName("Holmes");
        person.setHiringDate(LocalDateTime.now());
        person.setPassword("123");
        var detective = detectiveService.createDetective(person, Rank.INSPECTOR);
        assertNotNull(detective);
        detective.setBadgeNumber(BADGE_NO);
        assertEquals(DETECTIVE_ID, detective.getId());

        // create storage entries
        var storage = new Storage();
        storage.setName("Here");
        storage.setLocation("Here");
        storageRepo.save(storage);
        assertNotNull(storage.getId());

        var storage1 = new Storage();
        storage1.setName("There");
        storage1.setLocation("There");
        storageRepo.save(storage1);
        assertNotNull(storage1.getId());
    }

    @DisplayName("Pojo Exercise Solution")
    @Test
    void testCreateCaseSolution(){
        Map<Evidence, String> evidenceLocationMap = new HashMap<>();
        var ev = new Evidence();
        ev.setItemName("drugs");
        ev.setNotes("confiscated from minor");
        ev.setNumber("DR12345687");

        evidenceLocationMap.put(ev, "Here");

        //create criminal case
        var criminalCase = operationsService.createCriminalCase(CaseType.FELONY,
                "Drugs being sold to minors", BADGE_NO, evidenceLocationMap);

        assertNotNull(criminalCase);
        assertNotNull(criminalCase.getLeadInvestigator());

    }

}
