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
package com.apress.cems.aop;

import com.apress.cems.aop.config.AopConfig;
import com.apress.cems.aop.service.StorageService;
import com.apress.cems.aop.test.TestDbConfig;
import com.apress.cems.dao.Evidence;
import com.apress.cems.dao.Storage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AopConfig.class, TestDbConfig.class})
class StorageMonitorTest {

    @Autowired
    StorageService storageService;

    @Test
    void testProxyBubu() {
        var storage = new Storage();
        storage.setId(1L);
        storage.setName("Edinburgh PD Storage");
        storage.setLocation("EH4 3SD");

        var ev1 = new Evidence();
        ev1.setNumber("BL00254");
        ev1.setItemName("Glock 19");
        storage.addEvidence(ev1);

        var ev2 = new Evidence();
        ev2.setNumber("BL00257");
        ev1.setItemName("Bloody bullet 9mm");
        storage.addEvidence(ev2);

        storageService.save(storage);

        var result = storageService.findById(1L);
        assertNotNull(result.get());
    }

    @Test
    void testSaveEvidenceSet(){
        var storage = new Storage();
        storage.setId(1L);
        storage.setName("Edinburgh PD Storage");
        storage.setLocation("EH4 3SD");

        var ev1 = new Evidence();
        ev1.setNumber("BL00254");
        ev1.setItemName("Glock 19");
        storage.addEvidence(ev1);

        var ev2 = new Evidence();
        ev2.setNumber("BL00257");
        ev1.setItemName("Bloody bullet 9mm");
        storage.addEvidence(ev2);

        storageService.saveEvidenceSet(storage);
    }
}
