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
package com.apress.cems.jmock;

import com.apress.cems.dao.CriminalCase;
import com.apress.cems.dao.Detective;
import com.apress.cems.dao.Evidence;
import com.apress.cems.repos.EvidenceRepo;
import com.apress.cems.services.impl.SimpleEvidenceService;
import com.apress.cems.util.CaseStatus;
import com.apress.cems.util.CaseType;
import com.apress.cems.util.Rank;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.apress.cems.stub.util.TestObjectsBuilder.buildCase;
import static com.apress.cems.stub.util.TestObjectsBuilder.buildDetective;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public class SimpleEvidenceServiceTest {
    static final Long EVIDENCE_ID = 1L;

    private EvidenceRepo mockRepo;

    private Mockery mockery = new JUnit5Mockery();

    private SimpleEvidenceService service;

    @BeforeEach
    public void setUp() {
        mockRepo = mockery.mock(EvidenceRepo.class);

        service = new SimpleEvidenceService();
        service.setRepo(mockRepo);
    }

    @Test
    public void testCreateEvidence() {
        var detective = buildDetective("Sherlock", "Holmes", Rank.INSPECTOR, "TS1234");
        var criminalCase = buildCase(detective, CaseType.FELONY, CaseStatus.UNDER_INVESTIGATION);
        var evidence = new Evidence();
        evidence.setNumber("123445464");
        evidence.setItemName("Red Bloody Knife");
        evidence.setId(EVIDENCE_ID);
        evidence.setCriminalCase(criminalCase);

        mockery.checking(new Expectations() {{
            allowing(mockRepo).findById(EVIDENCE_ID);
            will(returnValue(Optional.of(evidence)));
        }});

        var result = service.findById(EVIDENCE_ID);
        mockery.assertIsSatisfied();

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.getId(), evidence.getId()),
                () -> assertEquals(result.getNumber(), evidence.getNumber())
        );
    }

}
