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

import com.apress.cems.dao.Detective;
import com.apress.cems.dj.repos.DetectiveRepo;
import com.apress.cems.dj.services.DetectiveService;
import com.apress.cems.dj.services.wrappers.DetectiveWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.annotation.Secured;

import javax.annotation.security.RolesAllowed;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Service
@Transactional
public class DetectiveServiceImpl implements DetectiveService {

    private Logger logger = LoggerFactory.getLogger(DetectiveServiceImpl.class);

    private DetectiveRepo detectiveRepo;

    public DetectiveServiceImpl(DetectiveRepo detectiveRepo) {
        this.detectiveRepo = detectiveRepo;
    }

    @Override
    @Secured("ROLE_ADMIN")
    //@RolesAllowed("ROLE_ADMIN")
    public List<Detective> findAll() {
        return detectiveRepo.findAll();
    }

    @Override
    public DetectiveWrapper findById(Long id) {
       /* Optional<Detective> detectiveOpt = detectiveRepo.findById(id);
        if(detectiveOpt.isPresent()) {
            Detective detective = detectiveOpt.get();
            logger.debug("Retrieved {} cases.", detective.getCriminalCases().size());
            return new DetectiveWrapper(detective);
        }
        return new DetectiveWrapper();
*/
        Optional<Detective> detectiveOpt = detectiveRepo.findByIdWithCriminalCases(id);
        return detectiveOpt.map(DetectiveWrapper::new).orElseGet(DetectiveWrapper::new);

    }

    @Override
    public Detective save(Detective detective) {
        return detectiveRepo.save(detective);
    }
}
