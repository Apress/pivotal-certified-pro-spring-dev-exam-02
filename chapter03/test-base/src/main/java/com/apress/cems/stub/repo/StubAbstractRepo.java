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
package com.apress.cems.stub.repo;

import com.apress.cems.dao.AbstractEntity;
import com.apress.cems.repos.AbstractRepo;
import com.apress.cems.repos.NotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public abstract class StubAbstractRepo <T extends AbstractEntity> implements AbstractRepo<T> {

    protected Map<Long, T> records = new HashMap<>();

    @Override
    public void save(T entity) {
        if (entity.getId() == null) {
            var id = (long) records.size() + 1;
            entity.setId(id);
        }
        records.put(entity.getId(), entity);
    }

    @Override
    public void delete(T entity) throws NotFoundException {
        findById(entity.getId()).ifPresent(r -> records.remove(r.getId()));
    }

    @Override
    public int  deleteById(Long entityId) throws NotFoundException {
        findById(entityId).ifPresent(r -> records.remove(r.getId()));
        return 1;
    }

    @Override
    public Optional<T> findById(Long entityId) {
        if(records.containsKey(entityId)) {
            return Optional.of(records.get(entityId));
        } else {
            throw new NotFoundException("Entity with id "
                    + entityId + " could not be processed because it does not exist.");
        }
    }
}
