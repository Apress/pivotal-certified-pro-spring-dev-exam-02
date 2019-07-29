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
package com.apress.cems.dj.services.impl;

import com.apress.cems.dao.CriminalCase;
import com.apress.cems.dao.Evidence;
import com.apress.cems.dao.Storage;
import com.apress.cems.dj.repos.EvidenceRepo;
import com.apress.cems.dj.services.EvidenceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Service
@Transactional
public class EvidenceServiceImpl implements EvidenceService {

    private EvidenceRepo evidenceRepo;

    public EvidenceServiceImpl(EvidenceRepo evidenceRepo) {
        this.evidenceRepo = evidenceRepo;
    }

    @Override
    public List<Evidence> findAll() {
        return evidenceRepo.findAll();
    }

    @Override
    public List<Evidence> findAllByStorage(Storage storage) {
        return evidenceRepo.findAllByStorage(storage);
    }

    @Override
    public List<Evidence> findAllByCriminalCase(CriminalCase criminalCase) {
        return evidenceRepo.findAllByCriminalCase(criminalCase);
    }

    @Override
    public Evidence save(Evidence evidence) {
        return evidenceRepo.save(evidence);
    }
}
