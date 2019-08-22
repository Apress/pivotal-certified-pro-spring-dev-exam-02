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
package com.apress.cems.easymock;

import com.apress.cems.dao.Detective;
import com.apress.cems.repos.DetectiveRepo;
import com.apress.cems.services.impl.SimpleDetectiveService;
import com.apress.cems.util.Rank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Optional;

import static com.apress.cems.stub.util.TestObjectsBuilder.buildDetective;
import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.*;
/**
 * @author Iuliana Cosmina
 * @since 1.0
 */

class SimpleDetectiveServiceTest {
    static final Long DETECTIVE_ID = 1L;

    private DetectiveRepo mockRepo;
    private SimpleDetectiveService service;

    @BeforeEach
    void setUp() {
        //prepare easymock
        mockRepo = createMock(DetectiveRepo.class);

        //create object to be tested
        service = new SimpleDetectiveService();

        // inject the easymock as dependency
        service.setRepo(mockRepo);
    }

    @Test
    void findByIdPositive() {
        //record what we want the easymock to do
        var simpleDetective = buildDetective("Sherlock", "Holmes", Rank.INSPECTOR, "TS1234");
        simpleDetective.setId(DETECTIVE_ID);
        expect(mockRepo.findById(DETECTIVE_ID)).andReturn(Optional.of(simpleDetective));
        replay(mockRepo);

        var detective = service.findById(DETECTIVE_ID);
        verify(mockRepo);
        assertAll(
                () -> assertNotNull(detective),
                () -> assertEquals(detective.getId(), simpleDetective.getId()),
                () -> assertEquals(detective.getBadgeNumber(), simpleDetective.getBadgeNumber())
        );

       /* assertAll(
                new Executable() {
                    @Override
                    void execute() throws Throwable {
                        assertNotNull(detective);
                    }
                },
                new Executable() {
                    @Override
                    void execute() throws Throwable {
                        assertEquals(detective.getId(), simpleDetective.getId());
                    }
                },
                new Executable() {
                    @Override
                    void execute() throws Throwable {
                        assertEquals(detective.getBadgeNumber(), simpleDetective.getBadgeNumber());
                    }
                }
        );*/
    }
}


/*
// To test multiple failures
assertAll(
                () -> assertNotNull(detective),
                () -> assertEquals(detective.getId(), 2L),
                () -> assertEquals(detective.getBadgeNumber(), "SH2211")
        );
 */