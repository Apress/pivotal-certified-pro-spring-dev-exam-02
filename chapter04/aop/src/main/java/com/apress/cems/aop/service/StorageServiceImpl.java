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
package com.apress.cems.aop.service;

import com.apress.cems.aop.ApressService;
import com.apress.cems.dao.Storage;
import com.apress.cems.repos.StorageRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@ApressService
@Service
public class StorageServiceImpl implements StorageService {
    private static final Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);
    private StorageRepo storageRepo;

    public StorageServiceImpl(StorageRepo storageRepo) {
       this.storageRepo = storageRepo;
    }

    @Override
    public Optional<Storage> findByName(String name) {
        return storageRepo.findByName(name);
    }

    @Override
    public Optional<Storage> findByLocation(String location) {
        return storageRepo.findByLocation(location);
    }

    @Override
    public void save(Storage storage) {
        storageRepo.save(storage);
        saveEvidenceSet(storage);
    }

    @Override
    public void saveEvidenceSet(Storage storage){
        //mock method to test the proxy nature
        storage.getEvidenceSet().forEach(ev -> {
            logger.info(" ---> Pretending to save evidence with number {}" , ev.getNumber());
        });
    }

    @Override
    public void delete(Storage entity) {
        storageRepo.delete(entity);
    }

    @Override
    public int deleteById(Long entityId) {
        return storageRepo.deleteById(entityId);
    }

    @Override
    public Optional<Storage> findById(Long entityId) {
        return storageRepo.findById(entityId);
    }

}
