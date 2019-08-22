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
package com.apress.cems.stub.services;

import com.apress.cems.dao.CriminalCase;
import com.apress.cems.dao.Detective;
import com.apress.cems.repos.NotFoundException;
import com.apress.cems.services.impl.SimpleCriminalCaseService;
import com.apress.cems.stub.repo.StubCriminalCaseRepo;
import com.apress.cems.util.Rank;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static com.apress.cems.stub.util.TestObjectsBuilder.buildDetective;
import static org.junit.Assert.*;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public class SimpleCriminalCaseServiceTest {
    static final Long CASE_ID = 1L;
    final Detective detective = buildDetective("Sherlock", "Holmes", Rank.INSPECTOR, "TS1234");

    StubCriminalCaseRepo repo = new StubCriminalCaseRepo();

    SimpleCriminalCaseService service = new SimpleCriminalCaseService();

    @Before
    public void setUp(){
        repo.init();

        //create object to be tested
        service = new SimpleCriminalCaseService();
        service.setRepo(repo);
    }

    //positive test, we know that a Case with ID=1 exists
    @Test
    public void findByIdPositive() {
        var criminalCase = service.findById(CASE_ID);
        assertNotNull(criminalCase);
    }

    //negative test, we know that a Case with ID=99 does not exist
    @Test(expected = NotFoundException.class)
    public void findByIdNegative() {
        var criminalCase = service.findById(99L);
        assertNull(criminalCase);
    }

    //negative test, we know that a Case with ID=99 does not exist
    @Test
    public void findByIdNegativeWithLambda() {
        assertThrows(NotFoundException.class, () -> service.findById(99L));
    }

    //positive test, we know that cases for this detective exist and how many
    @Test
    public void findByLeadPositive() {
        var result =  service.findByLeadInvestigator(detective);
        assertEquals(result.size(), 2);
    }

    //negative test, we know that cases for this detective do not exist
    @Test
    public void findByLeadNegative() {
        var detective = buildDetective("Jake", "Peralta", Rank.JUNIOR, "TS1122");
        var result =  service.findByLeadInvestigator(detective);
        assertNull(result);
    }

    //positive case, deleting existing case record
    @Test
    public void deleteByIdPositive() {
        service.deleteById(CASE_ID);

        try {
            var criminalCase = service.findById(CASE_ID);
            assertNull(criminalCase);
        } catch (NotFoundException nfe){
          assertTrue(nfe.getMessage().contains("Entity with id 1 could not be processed because it does not exist"));
        }
    }

    //negative case, attempt to delete non-existing case
    @Test(expected = NotFoundException.class)
    public void deleteByIdNegative() {
        service.deleteById(99L);
    }

    @After
    public void tearDown(){
        repo.clear();
    }
}
